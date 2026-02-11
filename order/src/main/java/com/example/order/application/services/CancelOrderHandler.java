package com.example.order.application.services;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.stereotype.Service;

import com.example.order.application.commands.CancelOrderCommand;
import com.example.order.application.ports.out.OrderRepository;
import com.example.order.domain.model.order.Order;
import com.example.order.infrastructure.messaging.out.OrderCanceledEvent;
import com.example.order.infrastructure.messaging.out.OrderEventProducer;
import com.example.order.infrastructure.messaging.out.OrderCanceledEvent.InventoryItemReserve;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CancelOrderHandler {
    private OrderRepository repository;
    private OrderEventProducer producer;

    @Transactional
    public void handle(CancelOrderCommand cmd) {
        Order order = repository
            .findById(cmd.orderId())
            .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        order.cancelOrder();

        repository.save(order);

        OrderCanceledEvent event = new OrderCanceledEvent(
            UUID.randomUUID(),
            order.getId(),
            Instant.now(),
            order.getItems().stream()
                .map(item -> new InventoryItemReserve(item.getInventoryItem().getItemId(), item.getQuantity()))
                .toList()
        );

        producer.publishOrderCanceled(event);

        // kafka event to refund payment, if the case
    }
}
