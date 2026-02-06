package com.example.inventory.application.services;

import org.springframework.stereotype.Service;

import com.example.inventory.application.commands.CreateInventoryCommand;
import com.example.inventory.application.ports.out.InventoryRepository;
import com.example.inventory.domain.model.inventory.Inventory;
import com.example.inventory.interfaces.rest.dto.inventory.InventoryCreationDto;
import com.example.inventory.interfaces.rest.dto.inventory.InventoryResponseDto;
import com.example.inventory.interfaces.rest.mapper.InventoryMapper;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateInventoryHandler {
    private final InventoryRepository repository;
    private final InventoryMapper mapper;

    public InventoryResponseDto handle(CreateInventoryCommand dto) {
        Inventory inventory = Inventory.newInventory(dto.location(), dto.name());
        Inventory savedInventory = repository.save(inventory);

        return mapper.toDto(savedInventory);
    }
}
