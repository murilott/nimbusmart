package com.example.inventory.infrastructure.persistance.inventory;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventory.domain.model.inventory.Inventory;

public interface InventoryRepositoryJpa extends JpaRepository<Inventory, UUID>{
    
}
