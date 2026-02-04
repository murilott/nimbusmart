package com.example.inventory.application.services;

import org.springframework.stereotype.Service;

import com.example.inventory.application.ports.out.InventoryItemRepository;
import com.example.inventory.application.ports.out.InventoryRepository;
import com.example.inventory.domain.model.inventory.Inventory;
import com.example.inventory.domain.model.inventory.InventoryItem;
import com.example.inventory.interfaces.rest.dto.inventoryItem.InventoryItemCreationDto;
import com.example.inventory.interfaces.rest.dto.inventoryItem.InventoryItemResponseDto;
import com.example.inventory.interfaces.rest.mapper.InventoryItemMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateInventoryItemHandler {
    private final InventoryItemRepository repository;
    private final InventoryRepository inventoryRepository;
    private final InventoryItemMapper mapper;

    public InventoryItemResponseDto handle(InventoryItemCreationDto dto) {
        Inventory inventory = inventoryRepository
            .findById(dto.inventoryId())
            .orElseThrow(() -> new EntityNotFoundException("Inventory not found"));

        InventoryItem item = InventoryItem.newItem(dto.productId(), inventory, dto.quantity());

        inventory.addToInventory(item);

        // InventoryItem savedItem = repository.save(item);
        inventoryRepository.save(inventory);

        return mapper.toDto(item);
    }
}
