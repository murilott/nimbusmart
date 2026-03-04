package com.example.delivery.application.services;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import com.example.delivery.application.commands.CreateDeliveryTrackingCommand;
import com.example.delivery.application.ports.out.DeliveryTrackingRepository;
import com.example.delivery.domain.model.DeliveryTracking;
import com.example.delivery.interfaces.rest.dto.DeliveryTrackingResponseDto;
import com.example.delivery.interfaces.rest.mapper.DeliveryTrackingMapper;

@Service
@AllArgsConstructor
public class CreateDeliveryTrackingHandler {
    private DeliveryTrackingRepository repository;
    private DeliveryTrackingMapper mapper;

    public DeliveryTrackingResponseDto handle() {
        DeliveryTracking deliveryTracking = DeliveryTracking.newDeliveryTracking();
        DeliveryTracking savedDeliveryTracking = repository.save(deliveryTracking);

        return mapper.toDto(savedDeliveryTracking);
    }
}
