package com.example.order.application.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.contracts.events.OrderDeliveryReadyEvent;
import com.example.contracts.events.OrderPaidEvent;
import com.example.order.application.ports.out.OrderRepository;
import com.example.order.domain.model.order.Order;
import com.example.order.infrastructure.messaging.out.OrderEventProducer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConfirmOrderHandler {
    private final OrderRepository repository;
    private final OrderEventProducer producer;

    @Transactional
    public void handle(UUID orderId) {
        log.info("Changing order status");

        Optional<Order> optional = repository.findById(orderId);

        if (optional.isEmpty()) {
            log.warn("Order {} not found. Ignoring event. OrderId=",
                    orderId);
        }

        if (!optional.isEmpty()) {
            Order order = optional.get();

            order.confirmOrder();

            repository.save(order);

            log.info("Order {} paid with status {}",
                    order.getId(), order.getStatus().getValue());

            OrderDeliveryReadyEvent event = new OrderDeliveryReadyEvent(
                    UUID.randomUUID(),
                    order.getId(),
                    Instant.now());

            producer.publishOrderDeliveryReady(event);

            // kafka event to delivery order.disptach
        }
    }
}
