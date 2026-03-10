package com.example.order.application.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.order.application.ports.out.OrderRepository;
import com.example.order.domain.model.order.Order;
import com.example.order.infrastructure.messaging.event.OrderDeliveryReadyEvent;
import com.example.order.infrastructure.messaging.event.OrderPaidEvent;
import com.example.order.infrastructure.messaging.out.OrderEventProducer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliverOrderHandler {
    private final OrderRepository repository;
    private OrderEventProducer producer;

    @Transactional
    public void handle(UUID orderId) {
        log.info("Delivering order in order service");

        Optional<Order> optional = repository.findById(orderId);

        if (optional.isEmpty()) {
            log.warn("Order {} not found. Ignoring event. OrderId=",
                    orderId);
        }

        Order order = optional.get();

        order.deliverOrder();

        repository.save(order);

        log.info("Order {} confirmed with status {}",
                order.getId(), order.getStatus().getValue());

    }
}
