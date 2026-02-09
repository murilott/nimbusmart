package com.example.order.application.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.order.domain.model.order.Order;

public interface OrderRepository {
    public Order save(Order order);
    public List<Order> findAll();
    public Page<Order> findAll(Pageable pageable);
    public Optional<Order> findById(UUID id);
}
