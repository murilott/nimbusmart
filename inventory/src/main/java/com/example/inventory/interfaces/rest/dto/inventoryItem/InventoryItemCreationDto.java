package com.example.inventory.interfaces.rest.dto.inventoryItem;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.inventory.domain.model.inventory.Inventory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record InventoryItemCreationDto(
    @NotNull(message = "ProductId must not be null")
    UUID productId,
    @NotNull(message = "InventoryId must not be null")
    UUID inventoryId,
    @PositiveOrZero(message = "Quantity must not be negative")
    int quantity,
    @PositiveOrZero(message = "Price must not be negative")
    BigDecimal price
) {
    
}
