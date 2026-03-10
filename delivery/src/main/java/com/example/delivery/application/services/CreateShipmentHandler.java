package com.example.delivery.application.services;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import com.example.delivery.application.commands.CreateShipmentCommand;
import com.example.delivery.application.ports.out.DeliveryTrackingRepository;
import com.example.delivery.application.ports.out.ShipmentRepository;
import com.example.delivery.domain.model.DeliveryTracking;
import com.example.delivery.domain.model.Shipment;
import com.example.delivery.interfaces.rest.dto.ShipmentResponseDto;
import com.example.delivery.interfaces.rest.mapper.ShipmentMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class CreateShipmentHandler {
    private ShipmentRepository repository;
    private DeliveryTrackingRepository deliveryTrackingRepository;
    private ShipmentMapper mapper;

    public ShipmentResponseDto handle(CreateShipmentCommand cmd) {
        DeliveryTracking deliveryTracking = deliveryTrackingRepository
            .findById(cmd.deliveryTrackingId())
            .orElseThrow(() -> new EntityNotFoundException("DeliveryTracking not found"));

        // TODO: grpc to verify orderId (not needed actually)

        Shipment shipment = Shipment.newShipment(cmd.orderId(), deliveryTracking, cmd.destinyAddress());

        deliveryTracking.getShipments().add(shipment);

        deliveryTrackingRepository.save(deliveryTracking);

        return mapper.toDto(shipment);
    }
}
