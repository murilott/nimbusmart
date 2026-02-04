package com.example.inventory.application.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.inventory.domain.model.inventory.Inventory;

public interface InventoryRepository {
    public Inventory save(Inventory inventory);
    public List<Inventory> findAll();
    public Page<Inventory> findAll(Pageable pageable);
    public Optional<Inventory> findById(UUID id);
}
