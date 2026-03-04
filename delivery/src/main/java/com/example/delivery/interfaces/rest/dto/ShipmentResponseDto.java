package com.example.delivery.interfaces.rest.dto;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.example.delivery.domain.model.DeliveryTracking;
import com.example.delivery.domain.vo.Status;

public record ShipmentResponseDto(
    UUID id,
    UUID orderId,
    UUID deliveryTrackingId,
    Status status,
    String destinyAddress,

    OffsetDateTime createdAt,
    OffsetDateTime dispatchedAt,
    OffsetDateTime deliveredAt,
    OffsetDateTime failedAt,

    Duration duration
) {
    
}
