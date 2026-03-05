package com.example.delivery.application.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.delivery.application.commands.CancelShipmentCommand;
import com.example.delivery.application.commands.DeliveredShipmentCommand;
import com.example.delivery.application.ports.out.DeliveryTrackingRepository;
import com.example.delivery.domain.model.DeliveryTracking;
import com.example.delivery.domain.model.Shipment;
import com.example.delivery.interfaces.rest.dto.ShipmentResponseDto;
import com.example.delivery.interfaces.rest.mapper.ShipmentMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CancelShipmentHandler {
    private DeliveryTrackingRepository repository;
    private ShipmentMapper shipmentMapper;
    
    public Optional<ShipmentResponseDto> handle(CancelShipmentCommand cmd) {
        DeliveryTracking deliveryTracking = repository
            .findById(cmd.deliveryTrackingId())
            .orElseThrow(() -> new EntityNotFoundException("DeliveryTracking not found"));
        
        Optional<Shipment> shipment = deliveryTracking.cancelShipment(cmd.shipmentId());

        if (shipment.isEmpty()) {
            return Optional.empty();
        }

        repository.save(deliveryTracking);

        ShipmentResponseDto dto = shipmentMapper.toDto(shipment.get());

        return Optional.of(dto);
    }
}
