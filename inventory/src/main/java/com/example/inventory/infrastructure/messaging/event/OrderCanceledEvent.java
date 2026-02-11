package com.example.inventory.infrastructure.messaging.event;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderCanceledEvent(
    UUID eventId,
    UUID orderId,
    Instant occurredAt,
    List<InventoryItemRestore> items
) {
    public record InventoryItemRestore(
        UUID iventoryItemtId,
        int quantity
    ) {}
}