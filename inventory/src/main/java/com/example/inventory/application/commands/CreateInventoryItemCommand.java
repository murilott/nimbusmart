package com.example.inventory.application.commands;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.PositiveOrZero;

public record CreateInventoryItemCommand(
    UUID productId,
    UUID inventoryId,
    int quantity,
    BigDecimal price
) {
    
}
