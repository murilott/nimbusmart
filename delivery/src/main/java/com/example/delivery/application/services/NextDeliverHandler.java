package com.example.delivery.application.services;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.example.delivery.application.commands.DeliverShipmentCommand;
import com.example.delivery.application.ports.out.DeliveryTrackingRepository;
import com.example.delivery.domain.model.DeliveryTracking;
import com.example.delivery.domain.model.Shipment;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NextDeliverHandler {
    private DeliveryTrackingRepository deliveryTrackingRepository;

    public void handle(DeliverShipmentCommand cmd) {
        DeliveryTracking deliveryTracking = deliveryTrackingRepository
            .findById(cmd.deliveryTrackingId())
            .orElseThrow(() -> new EntityNotFoundException("DeliveryTracking not found"));
        
        Shipment shipment = deliveryTracking.nextDeliverShipment();

        shipment.toDispatch(Duration.ofDays(1));

        deliveryTracking.addToDelivering(shipment);

        deliveryTrackingRepository.save(deliveryTracking);

        
    }
}
