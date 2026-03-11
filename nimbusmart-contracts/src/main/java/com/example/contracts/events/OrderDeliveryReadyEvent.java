package com.example.contracts.events;

import java.time.Instant;
import java.util.UUID;

public record OrderDeliveryReadyEvent(
    UUID eventId,
    UUID orderId,
    Instant occurredAt 
) {
    
}
