package com.example.order.infrastructure.persistance.order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.order.application.ports.out.OrderRepository;
import com.example.order.domain.model.order.Order;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class OrderRepositoryImpl implements OrderRepository{
    private final OrderRepositoryJpa jpa;

    @Override
    public Optional<Order> findById(UUID id) {
        return jpa.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return jpa.findAll();
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return jpa.findAll(pageable);
    }

    @Override
    public Order save(Order inventory) {
        return jpa.save(inventory);
    }
}
