package com.example.inventory.infrastructure.persistance.inventoryItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.inventory.application.ports.out.InventoryItemRepository;
import com.example.inventory.domain.model.inventory.InventoryItem;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class InventoryItemRepositoryImpl implements InventoryItemRepository{
    private final InventoryItemRepositoryJpa jpa;

    @Override
    public Optional<InventoryItem> findById(UUID id) {
        return jpa.findById(id);
    }

    @Override
    public List<InventoryItem> findAll() {
        return jpa.findAll();
    }

    @Override
    public Page<InventoryItem> findAll(Pageable pageable) {
        return jpa.findAll(pageable);
    }

    @Override
    public InventoryItem save(InventoryItem inventoryItem) {
        return jpa.save(inventoryItem);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpa.existsById(id);
    }
}
