package com.example.order.application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.order.application.ports.out.OrderItemRepository;
import com.example.order.application.ports.out.OrderRepository;
import com.example.order.interfaces.rest.dto.OrderItemResponseDto;
import com.example.order.interfaces.rest.dto.OrderResponseDto;
import com.example.order.interfaces.rest.mapper.OrderItemMapper;
import com.example.order.interfaces.rest.mapper.OrderMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ListOrderHandler {
    private final OrderRepository repository;
    private final OrderMapper mapper;

    private final OrderItemRepository itemRepository;
    private final OrderItemMapper itemMapper;

    public List<OrderResponseDto> handle() {
        return mapper.toDtoList(repository.findAll());
    }

    public List<OrderItemResponseDto> itemsHandler() {
        return itemMapper.toDtoList(itemRepository.findAll());
    }
}
