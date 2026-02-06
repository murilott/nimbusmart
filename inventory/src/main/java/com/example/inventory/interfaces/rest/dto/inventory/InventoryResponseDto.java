package com.example.inventory.interfaces.rest.dto.inventory;

import java.util.List;
import java.util.UUID;

import com.example.inventory.interfaces.rest.dto.inventoryItem.InventoryItemResponseDto;

import jakarta.validation.constraints.NotBlank;

public record InventoryResponseDto(
    UUID id,
    List<InventoryItemResponseDto> items,
    String location,
    String name
) {
    
}
