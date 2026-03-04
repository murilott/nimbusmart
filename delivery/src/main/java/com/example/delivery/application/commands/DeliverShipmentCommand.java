package com.example.delivery.application.commands;

import java.util.UUID;

public record DeliverShipmentCommand(
    UUID deliveryTrackingId
) {
    
}
