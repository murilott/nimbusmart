package com.example.inventory.infrastructure.persistance.inventoryItem;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inventory.domain.model.inventory.InventoryItem;

public interface InventoryItemRepositoryJpa extends JpaRepository<InventoryItem, UUID>{
    
}
