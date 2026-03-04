package com.example.delivery.interfaces.rest.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ShipmentCreationDto(
    @NotNull(message = "orderId must not be null")
    UUID orderId, 
    @NotNull(message = "deliveryTrackingId must not be null")
    UUID deliveryTrackingId, 
    @NotBlank(message = "destinyAddress must not be blank")
    String destinyAddress
) {
    
}
