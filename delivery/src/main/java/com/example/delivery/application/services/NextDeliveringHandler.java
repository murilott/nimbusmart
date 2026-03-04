package com.example.delivery.application.services;

import java.time.Duration;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.delivery.application.commands.DeliverShipmentCommand;
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
public class NextDeliveringHandler {
    private DeliveryTrackingRepository deliveryTrackingRepository;
    private ShipmentMapper shipmentMapper;

    public Optional<ShipmentResponseDto> handle(DeliveredShipmentCommand cmd) {
        DeliveryTracking deliveryTracking = deliveryTrackingRepository
            .findById(cmd.deliveryTrackingId())
            .orElseThrow(() -> new EntityNotFoundException("DeliveryTracking not found"));
        
        Optional<Shipment> shipment = deliveryTracking.pullNextTransitToDelivered();

        if (shipment.isEmpty()) {
            return Optional.empty();
        }

        // kafka to order(atDelivered)

        deliveryTrackingRepository.save(deliveryTracking);

        ShipmentResponseDto dto = shipmentMapper.toDto(shipment.get());

        return Optional.of(dto);
    }
}
