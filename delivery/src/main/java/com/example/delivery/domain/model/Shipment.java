package com.example.delivery.domain.model;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.example.delivery.domain.vo.Status;
import com.example.delivery.domain.vo.StatusType;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class Shipment {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private UUID orderId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "delivery_tracking_id")
    private DeliveryTracking deliveryTracking;

    @Embedded
    private Status status;

    @NotBlank(message = "DestinyAddress must not be blank")
    private String destinyAddress;

    private OffsetDateTime createdAt;
    private OffsetDateTime dispatchedAt;
    private OffsetDateTime deliveredAt;
    private OffsetDateTime failedAt;

    private Duration duration;

    public static Shipment newShipment(UUID orderId, DeliveryTracking deliveryTracking, String destinyAddress) {
        Shipment shipment = new Shipment(orderId, deliveryTracking, destinyAddress);

        return shipment;
    }

    public Shipment(UUID orderId, DeliveryTracking deliveryTracking, String destinyAddress){
        this.setId(UUID.randomUUID());
        this.setOrderId(orderId);
        this.setDeliveryTracking(deliveryTracking);
        this.setStatus(Status.of(StatusType.PENDING));
        this.setDestinyAddress(destinyAddress);
    }

    public void toDispatch(Duration duration) {
        if (!this.getStatus().getValue().equals(StatusType.PENDING)) {
            throw new IllegalArgumentException("Can only dispatch pending shipments. Status=" +
                this.getStatus().getValue()
            );
        }

        nextStep();
        this.setDuration(duration);
        this.setDispatchedAt(OffsetDateTime.now());
    }

    public void toDeliver() {
        if (!this.getStatus().getValue().equals(StatusType.IN_TRANSIT)) {
            throw new IllegalArgumentException("Can only deliver in transit shipments. Status=" +
                this.getStatus().getValue()
            );
        }

        nextStep();
        this.setDeliveredAt(OffsetDateTime.now());
    }
    
    public void cancelShipment(){
        this.setStatus(Status.of(StatusType.FAILED));
        this.setFailedAt(OffsetDateTime.now());
    }

    private void nextStep() {
        this.getStatus().nextStatus();
    }

}
