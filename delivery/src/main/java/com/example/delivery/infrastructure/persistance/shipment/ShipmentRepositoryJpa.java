package com.example.delivery.infrastructure.persistance.shipment;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.delivery.domain.model.Shipment;

public interface ShipmentRepositoryJpa extends JpaRepository<Shipment, UUID>{
    
}
