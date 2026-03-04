package com.example.delivery.interfaces.rest.dto;

import java.util.List;
import java.util.UUID;


public record DeliveryTrackingResponseDto(
    UUID id,
    List<ShipmentResponseDto> itemsToDeliver,
    List<ShipmentResponseDto> itemsDelivering,
    List<ShipmentResponseDto> itemsDelivered
) {
    
}
