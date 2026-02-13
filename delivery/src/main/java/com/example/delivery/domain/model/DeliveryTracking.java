package com.example.delivery.domain.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.UUID;

import com.example.delivery.domain.vo.StatusType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
public class DeliveryTracking {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    private Deque<Shipment> itemsToDeliver = new ArrayDeque<>();
    private Deque<Shipment> itemsDelivered = new ArrayDeque<>();

    public static DeliveryTracking newDeliveryTracking(){
        DeliveryTracking tracking = new DeliveryTracking();

        return tracking;
    }

    public DeliveryTracking() {
        this.setId(UUID.randomUUID());
    }

    public Shipment nextShipment() {
        Shipment shipment = this.getItemsToDeliver().pollFirst();

        return shipment;
    }

    public void addToDeliverQueue(Shipment shipment) {
        if (shipment == null) {
            throw new IllegalArgumentException("Shipment to be added is null");
        }

        if (!shipment.getStatus().getValue().equals(StatusType.PENDING)) {
            throw new IllegalArgumentException(
                "Can only add to deliver if shipment is pending; current status=" 
                + shipment.getStatus().getValue()
            );
        }
        
        this.getItemsToDeliver().addLast(shipment);
    }
    
    public void addToDeliverQueue(List<Shipment> shipments) {
        if (shipments == null) {
            throw new IllegalArgumentException("Shipment to be added is null");
        }

        List<Shipment> toAdd = shipments.stream().filter((Shipment shipment) -> {
            return shipment.getStatus().getValue().equals(StatusType.PENDING);
        }).toList();
        
        this.getItemsToDeliver().addAll(toAdd);        
    }

    public void addToDelivered(Shipment shipment) {
        if (shipment == null) {
            throw new IllegalArgumentException("Shipment to be added is null");
        }

        if (!shipment.getStatus().getValue().equals(StatusType.DELIVERED)) {
            throw new IllegalArgumentException(
                "Can only add to delivered list if shipment is delivered; current status=" 
                + shipment.getStatus().getValue()
            );
        }
        
        this.getItemsDelivered().addLast(shipment);
    }
}
