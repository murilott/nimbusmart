package com.example.delivery.infrastructure.persistance.shipment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.delivery.application.ports.out.ShipmentRepository;
import com.example.delivery.domain.model.Shipment;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class ShipmentRepositoryImpl implements ShipmentRepository{
    private final ShipmentRepositoryJpa jpa;

    @Override
    public Optional<Shipment> findById(UUID id) {
        return jpa.findById(id);
    }

    @Override
    public List<Shipment> findAll() {
        return jpa.findAll();
    }

    @Override
    public Page<Shipment> findAll(Pageable pageable) {
        return jpa.findAll(pageable);
    }

    @Override
    public Shipment save(Shipment shipment) {
        return jpa.save(shipment);
    }
}
