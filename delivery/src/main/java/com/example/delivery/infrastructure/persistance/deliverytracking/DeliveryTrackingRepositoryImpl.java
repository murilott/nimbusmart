package com.example.delivery.infrastructure.persistance.deliverytracking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.delivery.application.ports.out.DeliveryTrackingRepository;
import com.example.delivery.domain.model.DeliveryTracking;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class DeliveryTrackingRepositoryImpl implements DeliveryTrackingRepository{
    private final DeliveryTrackingRepositoryJpa jpa;

    @Override
    public Optional<DeliveryTracking> findById(UUID id) {
        return jpa.findById(id);
    }

    @Override
    public List<DeliveryTracking> findAll() {
        return jpa.findAll();
    }

    @Override
    public Page<DeliveryTracking> findAll(Pageable pageable) {
        return jpa.findAll(pageable);
    }

    @Override
    public DeliveryTracking save(DeliveryTracking deliveryTracking) {
        return jpa.save(deliveryTracking);
    }
}
