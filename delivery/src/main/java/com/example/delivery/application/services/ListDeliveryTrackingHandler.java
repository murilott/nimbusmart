package com.example.delivery.application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.delivery.application.ports.out.DeliveryTrackingRepository;
import com.example.delivery.application.ports.out.ShipmentRepository;
import com.example.delivery.interfaces.rest.dto.DeliveryTrackingResponseDto;
import com.example.delivery.interfaces.rest.dto.ShipmentResponseDto;
import com.example.delivery.interfaces.rest.mapper.DeliveryTrackingMapper;
import com.example.delivery.interfaces.rest.mapper.ShipmentMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ListDeliveryTrackingHandler {
    private final DeliveryTrackingRepository repository;
    private final DeliveryTrackingMapper mapper;

    private final ShipmentRepository shipmentRepository;
    private final ShipmentMapper shipmentMapper;

    public List<DeliveryTrackingResponseDto> handle() {
        return mapper.toDtoList(repository.findAll());
    }

    public List<ShipmentResponseDto> shipmentsHandler() {
        return shipmentMapper.toDtoList(shipmentRepository.findAll());
    }
}
