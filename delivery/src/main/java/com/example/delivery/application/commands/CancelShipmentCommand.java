package com.example.delivery.application.commands;

import java.util.UUID;

public record CancelShipmentCommand(
    UUID deliveryTrackingId,
    UUID shipmentId
) {
    
}
