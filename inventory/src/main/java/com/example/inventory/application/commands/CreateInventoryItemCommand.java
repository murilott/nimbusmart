package com.example.inventory.application.commands;

import java.util.UUID;

import jakarta.validation.constraints.PositiveOrZero;

public record CreateInventoryItemCommand(
    UUID productId,
    UUID inventoryId,
    int quantity
) {
    
}
