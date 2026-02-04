package com.example.inventory.interfaces.rest.dto.inventoryItem;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.example.inventory.domain.model.inventory.Inventory;
import com.example.inventory.domain.vo.Status;

public record InventoryItemResponseDto(
    UUID id,
    UUID productId,
    int quantity,
    OffsetDateTime createdAt,
    Status status
) {
    
}
