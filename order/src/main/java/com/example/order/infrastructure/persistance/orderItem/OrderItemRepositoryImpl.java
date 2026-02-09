package com.example.order.infrastructure.persistance.orderItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.order.application.ports.out.OrderItemRepository;
import com.example.order.domain.model.order.OrderItem;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class OrderItemRepositoryImpl implements OrderItemRepository{
    private final OrderItemRepositoryJpa jpa;

    @Override
    public Optional<OrderItem> findById(UUID id) {
        return jpa.findById(id);
    }

    @Override
    public List<OrderItem> findAll() {
        return jpa.findAll();
    }

    @Override
    public Page<OrderItem> findAll(Pageable pageable) {
        return jpa.findAll(pageable);
    }

    @Override
    public OrderItem save(OrderItem inventory) {
        return jpa.save(inventory);
    }
}
