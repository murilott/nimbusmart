package com.example.delivery.interfaces.rest.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ShipmentCancelDto(
    @NotNull(message = "shipmentId must not be null")
    UUID shipmentId, 
    @NotNull(message = "deliveryTrackingId must not be null")
    UUID deliveryTrackingId
) {
    
}
