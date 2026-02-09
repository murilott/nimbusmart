package com.example.order.infrastructure.persistance.orderItem;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.order.domain.model.order.OrderItem;

public interface OrderItemRepositoryJpa extends JpaRepository<OrderItem, UUID>{
    
}
