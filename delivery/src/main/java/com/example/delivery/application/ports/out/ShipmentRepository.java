package com.example.delivery.application.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.delivery.domain.model.Shipment;

public interface ShipmentRepository {
    public Shipment save(Shipment shipment);
    public List<Shipment> findAll();
    public Page<Shipment> findAll(Pageable pageable);
    public Optional<Shipment> findById(UUID id);
}
