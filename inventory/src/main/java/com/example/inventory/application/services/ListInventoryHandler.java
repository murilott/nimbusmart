package com.example.inventory.application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.inventory.application.ports.out.InventoryItemRepository;
import com.example.inventory.application.ports.out.InventoryRepository;
import com.example.inventory.interfaces.rest.dto.inventory.InventoryResponseDto;
import com.example.inventory.interfaces.rest.dto.inventoryItem.InventoryItemResponseDto;
import com.example.inventory.interfaces.rest.mapper.InventoryItemMapper;
import com.example.inventory.interfaces.rest.mapper.InventoryMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ListInventoryHandler {
    private final InventoryRepository repository;
    private final InventoryMapper mapper;

    private final InventoryItemRepository itemRepository;
    private final InventoryItemMapper itemMapper;

    public List<InventoryResponseDto> handle() {
        return mapper.toDtoList(repository.findAll());
    }

    public List<InventoryItemResponseDto> itemsHandler() {
        return itemMapper.toDtoList(itemRepository.findAll());
    }
}
