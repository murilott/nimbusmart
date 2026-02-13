package com.example.delivery.application.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.delivery.domain.model.DeliveryTracking;

public interface DeliveryTrackingRepository {
    public DeliveryTracking save(DeliveryTracking deliveryTracking);
    public List<DeliveryTracking> findAll();
    public Page<DeliveryTracking> findAll(Pageable pageable);
    public Optional<DeliveryTracking> findById(UUID id);
}
