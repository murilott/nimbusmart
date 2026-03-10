package com.example.order.infrastructure.messaging.event;

import java.time.Instant;
import java.util.UUID;

public record OrderDeliveredEvent(
    UUID eventId,
    UUID orderId,
    Instant occurredAt 
) {
    
}
