package com.example.order.application.commands;

import java.util.UUID;

public record CreateOrderItemCommand(
    UUID orderId,
    UUID inventoryItemId,
    int quantity
) {
    
}
