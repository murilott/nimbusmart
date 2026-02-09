package com.example.order.application.services;

import org.springframework.stereotype.Service;

import com.example.order.application.ports.out.OrderRepository;
import com.example.order.domain.model.order.Order;
import com.example.order.interfaces.rest.dto.OrderResponseDto;
import com.example.order.interfaces.rest.mapper.OrderMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateOrderHandler {
    private final OrderRepository repository;
    private final OrderMapper mapper;

    public OrderResponseDto handle() {
        Order order = Order.newOrder();
        Order savedOrder = repository.save(order);

        return mapper.toDto(savedOrder);
    }
}
