package com.example.delivery.application.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.delivery.application.ports.out.DeliveryTrackingRepository;
import com.example.delivery.domain.model.DeliveryTracking;
import com.example.delivery.domain.model.Shipment;
import com.example.delivery.interfaces.rest.dto.DeliveryTrackingResponseDto;
import com.example.delivery.interfaces.rest.mapper.ShipmentMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class NewDeliveryHandler {
    private DeliveryTrackingRepository repository;
    private CreateDeliveryTrackingHandler createDeliveryTrackingHandler;

    private ShipmentMapper shipmentMapper;
    
    @Transactional
    public void handle(UUID orderId) {
        DeliveryTracking getTracking = repository.findAll().stream().findFirst().orElse(null);

        if (getTracking == null) {
            log.info("DeliveryTracking not found, creating one");
            
            DeliveryTrackingResponseDto dto = createDeliveryTrackingHandler.handle();

            getTracking = repository
                .findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("DeliveryTracking not found"));
        }

        String sampleAddress = "Sample address";

        Shipment shipment = Shipment.newShipment(orderId, getTracking, sampleAddress);

        getTracking.getShipments().add(shipment);

        repository.save(getTracking);

        // return shipmentMapper.toDto(shipment);
    }
}
