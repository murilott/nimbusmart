package com.example.delivery.interfaces.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.delivery.domain.model.Shipment;
import com.example.delivery.interfaces.rest.dto.ShipmentResponseDto;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {
    @Mapping(source = "deliveryTracking.id", target = "deliveryTrackingId")
    ShipmentResponseDto toDto(Shipment shipment);
    List<ShipmentResponseDto> toDtoList(List<Shipment> shipment);
}
