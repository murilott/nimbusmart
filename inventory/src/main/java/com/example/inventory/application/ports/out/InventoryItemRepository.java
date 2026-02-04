package com.example.inventory.application.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.inventory.domain.model.inventory.InventoryItem;

public interface InventoryItemRepository {
    public InventoryItem save(InventoryItem inventoryItem);
    public List<InventoryItem> findAll();
    public Page<InventoryItem> findAll(Pageable pageable);
    public Optional<InventoryItem> findById(UUID id);
}
