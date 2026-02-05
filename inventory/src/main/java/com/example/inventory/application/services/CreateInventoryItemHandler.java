package com.example.inventory.application.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.inventory.infrastructure.messaging.out.ProductGrpcGateway;
import com.example.inventory.application.commands.CreateInventoryItemCommand;
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

    private final ProductGrpcGateway productGrpcGateway;
    // private final KafkaTemplate<String, String> kafkaTemplate;

    public InventoryItemResponseDto handle(CreateInventoryItemCommand dto) {
        Inventory inventory = inventoryRepository
            .findById(dto.inventoryId())
            .orElseThrow(() -> new EntityNotFoundException("Inventory not found"));

        if (!productGrpcGateway.exists(dto.productId())) {
            throw new EntityNotFoundException("Product does not exist");
        }

        InventoryItem item = InventoryItem.newItem(dto.productId(), inventory, dto.quantity());

        inventory.addToInventory(item);

        // InventoryItem savedItem = repository.save(item);
        inventoryRepository.save(inventory);

        return mapper.toDto(item);
    }
}
