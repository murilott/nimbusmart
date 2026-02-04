package com.example.inventory.interfaces.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.inventory.domain.model.inventory.Inventory;
import com.example.inventory.interfaces.rest.dto.inventory.InventoryResponseDto;

@Mapper(componentModel = "spring", uses = { InventoryItemMapper.class } )
public interface InventoryMapper {
    InventoryResponseDto toDto(Inventory inventory);
    List<InventoryResponseDto> toDtoList(List<Inventory> inventory);
}
