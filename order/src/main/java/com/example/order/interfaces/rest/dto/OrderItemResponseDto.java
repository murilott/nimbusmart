package com.example.order.interfaces.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.order.domain.vo.ItemSnapshot;

public record OrderItemResponseDto(
    UUID id,
    ItemSnapshot inventoryItem,
    int quantity,
    BigDecimal cost
) {
    
}
