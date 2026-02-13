package com.example.inventory.application.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.inventory.application.ports.out.InventoryItemRepository;
import com.example.inventory.domain.model.inventory.InventoryItem;
import com.example.inventory.infrastructure.messaging.event.OrderCanceledEvent.InventoryItemRestore;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CancelOrderHandler {
    private final InventoryItemRepository repository;
    
    @Transactional
    public void handle(UUID orderId, List<InventoryItemRestore> items) {
        log.info("Returning InventoryItem quantities");

        for (InventoryItemRestore item : items) {

            Optional<InventoryItem> optional = repository.findById(item.iventoryItemtId());

            if (optional.isEmpty()) {
                log.warn("InventoryItem {} not found. Ignoring event.",
                        item.iventoryItemtId());
                continue;
            }

            InventoryItem invItem = optional.get();
            invItem.addInventoryItemQuantity(item.quantity());

            repository.save(invItem);

            log.info("Returned item {} quantity {}",
                    invItem.getId(), item.quantity());
        }

        log.info("Successfully returned items from orderId {}", orderId);
    }
}
