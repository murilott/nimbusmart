package com.example.order.interfaces.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.order.domain.vo.ItemSnapshot;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemCreationDto(
    @NotNull(message = "orderId must not be null")
    UUID orderId,
    @NotNull(message = "inventoryItemId must not be null")
    UUID inventoryItemId,
    @Positive(message = "Quantity must be greater than 0")
    int quantity
) {
    
}
