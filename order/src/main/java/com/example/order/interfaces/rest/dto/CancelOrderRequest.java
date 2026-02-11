package com.example.order.interfaces.rest.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CancelOrderRequest(
    @NotNull(message = "orderId must not be null")
    UUID orderId
) {
    
}
