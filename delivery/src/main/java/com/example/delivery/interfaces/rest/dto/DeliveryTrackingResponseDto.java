package com.example.delivery.interfaces.rest.dto;

import java.util.Deque;
import java.util.UUID;

import com.example.delivery.domain.model.Shipment;

public record DeliveryTrackingResponseDto(
    UUID id,
    Deque<Shipment> itemsToDeliver,
    Deque<Shipment> itemsDelivered
) {
    
}
