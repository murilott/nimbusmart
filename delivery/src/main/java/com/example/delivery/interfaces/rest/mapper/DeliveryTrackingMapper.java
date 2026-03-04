package com.example.delivery.interfaces.rest.mapper;

import java.util.Deque;
import java.util.List;

import org.mapstruct.Mapper;

import com.example.delivery.domain.model.DeliveryTracking;
import com.example.delivery.interfaces.rest.dto.DeliveryTrackingResponseDto;

@Mapper(componentModel = "spring")
public interface DeliveryTrackingMapper {
    DeliveryTrackingResponseDto toDto(DeliveryTracking deliveryTracking);
    List<DeliveryTrackingResponseDto> toDtoList(List<DeliveryTracking> deliveryTracking);
}
