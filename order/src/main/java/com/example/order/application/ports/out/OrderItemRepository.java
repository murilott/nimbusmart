package com.example.order.application.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.order.domain.model.order.OrderItem;

public interface OrderItemRepository {
    public OrderItem save(OrderItem order);
    public List<OrderItem> findAll();
    public Page<OrderItem> findAll(Pageable pageable);
    public Optional<OrderItem> findById(UUID id);
}
