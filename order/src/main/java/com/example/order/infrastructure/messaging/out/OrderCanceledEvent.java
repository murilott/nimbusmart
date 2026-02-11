package com.example.order.infrastructure.messaging.out;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderCanceledEvent(
    UUID eventId,
    UUID orderId,
    Instant occurredAt,
    List<InventoryItemReserve> items
) {
    public record InventoryItemReserve(
        UUID iventoryItemtId,
        int quantity
    ) {}
}