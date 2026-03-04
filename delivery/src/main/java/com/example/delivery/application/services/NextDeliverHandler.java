package com.example.delivery.application.services;

import java.time.Duration;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.delivery.application.commands.DeliverShipmentCommand;
import com.example.delivery.application.ports.out.DeliveryTrackingRepository;
import com.example.delivery.domain.model.DeliveryTracking;
import com.example.delivery.domain.model.Shipment;
import com.example.delivery.interfaces.rest.dto.ShipmentResponseDto;
import com.example.delivery.interfaces.rest.mapper.ShipmentMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NextDeliverHandler {
    private DeliveryTrackingRepository deliveryTrackingRepository;
    private ShipmentMapper shipmentMapper;

    public Optional<ShipmentResponseDto> handle(DeliverShipmentCommand cmd) {
        DeliveryTracking deliveryTracking = deliveryTrackingRepository
            .findById(cmd.deliveryTrackingId())
            .orElseThrow(() -> new EntityNotFoundException("DeliveryTracking not found"));
        
        // Shipment shipment = deliveryTracking.nextDeliverShipment();

        Optional<Shipment> shipment = deliveryTracking.pullNextPendingToTransit(cmd.days());

        if (shipment.isEmpty()) {
            return Optional.empty();
        }

        // shipment.toDispatch(days);

        // deliveryTracking.addToDelivering(shipment);

        // kafka to order (atDispatch)

        deliveryTrackingRepository.save(deliveryTracking);

        ShipmentResponseDto dto = shipmentMapper.toDto(shipment.get());

        return Optional.of(dto);
    }
}
