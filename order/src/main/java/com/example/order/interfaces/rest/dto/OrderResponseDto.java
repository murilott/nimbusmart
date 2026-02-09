package com.example.order.interfaces.rest.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.example.order.domain.model.order.OrderItem;
import com.example.order.domain.vo.Status;

import jakarta.persistence.Embedded;

public record OrderResponseDto(
    UUID id,
    List<OrderItemResponseDto> items,

    OffsetDateTime createdAt,
    OffsetDateTime confirmedAt,
    OffsetDateTime dispatchedAt,
    OffsetDateTime deliveredAt,
    OffsetDateTime cancelledAt,

    BigDecimal totalCost,

    Status status
) {
    
}
