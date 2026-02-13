package com.example.order.infrastructure.messaging.event;

import java.time.Instant;
import java.util.UUID;

public record OrderPaidEvent(
    UUID eventId,
    UUID orderId,
    Instant occurredAt
) {
    
}
