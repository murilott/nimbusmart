package com.example.inventory.infrastructure.messaging.in;

import java.util.List;
import java.util.Optional;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.inventory.application.ports.out.InventoryItemRepository;
import com.example.inventory.application.services.CancelOrderHandler;
import com.example.inventory.domain.model.inventory.InventoryItem;
import com.example.inventory.infrastructure.messaging.event.OrderCanceledEvent;
import com.example.inventory.infrastructure.messaging.event.OrderCanceledEvent.InventoryItemRestore;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCanceledListener {
    private final InventoryItemRepository repository;
    private final CancelOrderHandler cancelOrderHandler;

    // TODO: implement idempotency prevention
    @KafkaListener(topics = "order.cancel")
    public void onOrderCanceled(OrderCanceledEvent event) {
        cancelOrderHandler.handle(event.orderId(), event.items());
    }
}
