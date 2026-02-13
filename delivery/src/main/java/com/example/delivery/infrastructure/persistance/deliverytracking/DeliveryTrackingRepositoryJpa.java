package com.example.delivery.infrastructure.persistance.deliverytracking;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.delivery.domain.model.DeliveryTracking;

public interface DeliveryTrackingRepositoryJpa extends JpaRepository<DeliveryTracking, UUID>{
    
}
