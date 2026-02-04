package com.example.inventory.interfaces.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.inventory.domain.model.inventory.InventoryItem;
import com.example.inventory.interfaces.rest.dto.inventoryItem.InventoryItemResponseDto;

@Mapper(componentModel = "spring")
public interface InventoryItemMapper {
    InventoryItemResponseDto toDto(InventoryItem item);
    List<InventoryItemResponseDto> toDtoList(List<InventoryItem> item);
}
