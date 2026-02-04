package com.example.inventory.infrastructure.persistance.inventory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.inventory.application.ports.out.InventoryRepository;
import com.example.inventory.domain.model.inventory.Inventory;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepository{
    private final InventoryRepositoryJpa jpa;

    @Override
    public Optional<Inventory> findById(UUID id) {
        return jpa.findById(id);
    }

    @Override
    public List<Inventory> findAll() {
        return jpa.findAll();
    }

    @Override
    public Page<Inventory> findAll(Pageable pageable) {
        return jpa.findAll(pageable);
    }

    @Override
    public Inventory save(Inventory inventory) {
        return jpa.save(inventory);
    }
}
