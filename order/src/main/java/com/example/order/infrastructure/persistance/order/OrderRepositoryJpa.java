package com.example.order.infrastructure.persistance.order;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.order.domain.model.order.Order;

public interface OrderRepositoryJpa extends JpaRepository<Order, UUID>{
    
}
