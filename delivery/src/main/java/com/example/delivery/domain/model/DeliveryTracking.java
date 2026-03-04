package com.example.delivery.domain.model;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.delivery.domain.vo.StatusType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    // TODO: courier attribute/entity

    @OneToMany(mappedBy = "deliveryTracking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shipment> shipments = new ArrayList<>();

    public static DeliveryTracking newDeliveryTracking() {
        DeliveryTracking tracking = new DeliveryTracking();

        return tracking;
    }

    public DeliveryTracking() {
        this.setId(UUID.randomUUID());
    }

    // controller/service - função "sair para entrega" obtém próximo shipment do
    // itemstodeliver
    // - shipment sai da lista itemstodeliver
    // - shipment chama todisptach, fica em status in transit

    // controller - função "entregar" obtém próximo shipment do itemstodeliver
    // - shipment chama todisptach, fica em status in transit

    public Optional<Shipment> pullNextPendingToTransit(int days) {
        Duration duration = Duration.ofDays(days);

        for (Iterator<Shipment> it = shipments.iterator(); it.hasNext();) {
            Shipment shipment = it.next();

            if (shipment.getStatus().getValue() == StatusType.PENDING) {
                it.remove();
                shipment.toDispatch(duration);
                shipments.add(shipment);

                return Optional.of(shipment);
            }
        }

        return Optional.empty();
    }

    public Optional<Shipment> pullNextTransitToDelivered() {
        for (Iterator<Shipment> it = shipments.iterator(); it.hasNext();) {
            Shipment shipment = it.next();

            if (shipment.getStatus().getValue() == StatusType.IN_TRANSIT) {
                it.remove();
                shipment.toDeliver();
                shipments.add(shipment);

                return Optional.of(shipment);
            }
        }

        return Optional.empty();
    }

    // public Shipment nextDeliverShipment() {
    //     Shipment shipment = pollFirstDeliver();

    //     return shipment;
    // }

    // public Shipment nextDeliveringShipment() {
    //     Shipment shipment = pollFirstDelivering();

    //     return shipment;
    // }

    // private Shipment pollFirstDeliver() {
    //     if (this.itemsToDeliver == null || this.itemsToDeliver.isEmpty()) {
    //         return null;
    //     }

    //     return this.itemsToDeliver.remove(0);
    // }

    // private Shipment pollFirstDelivering() {
    //     if (this.itemsDelivering == null || this.itemsDelivering.isEmpty()) {
    //         return null;
    //     }

    //     return this.itemsDelivering.remove(0);
    // }

    public List<Shipment> getItemsToDeliver() {
        return shipments.stream()
                .filter(s -> s.getStatus().getValue() == StatusType.PENDING)
                .toList();
    }

    public List<Shipment> getItemsDelivering() {
        return shipments.stream()
                .filter(s -> s.getStatus().getValue() == StatusType.IN_TRANSIT)
                .toList();
    }

    public List<Shipment> getItemsDelivered() {
        return shipments.stream()
                .filter(s -> s.getStatus().getValue() == StatusType.DELIVERED)
                .toList();
    }

    // public void addToDeliverQueue(Shipment shipment) {
    //     if (shipment == null) {
    //         throw new IllegalArgumentException("Shipment to be added is null");
    //     }

    //     if (!shipment.getStatus().getValue().equals(StatusType.PENDING)) {
    //         throw new IllegalArgumentException(
    //                 "Can only add to deliver if shipment is pending; current status="
    //                         + shipment.getStatus().getValue());
    //     }

    //     this.getShipments().add(shipment);
    // }

    // public void addToDeliverQueue(List<Shipment> shipments) {
    //     if (shipments == null) {
    //         throw new IllegalArgumentException("Shipment to be added is null");
    //     }

    //     List<Shipment> toAdd = shipments.stream().filter((Shipment shipment) -> {
    //         return shipment.getStatus().getValue().equals(StatusType.PENDING);
    //     }).toList();

    //     this.getShipments().addAll(toAdd);
    // }

    // public void addToDelivered(Shipment shipment) {
    //     if (shipment == null) {
    //         throw new IllegalArgumentException("Shipment to be added is null");
    //     }

    //     if (!shipment.getStatus().getValue().equals(StatusType.DELIVERED)) {
    //         throw new IllegalArgumentException(
    //                 "Can only add to delivered list if shipment is delivered; current status="
    //                         + shipment.getStatus().getValue());
    //     }

    //     this.getShipments().add(shipment);
    // }

    // public void addToDelivering(Shipment shipment) {
    //     if (shipment == null) {
    //         throw new IllegalArgumentException("Shipment to be added is null");
    //     }

    //     if (!shipment.getStatus().getValue().equals(StatusType.IN_TRANSIT)) {
    //         throw new IllegalArgumentException(
    //                 "Can only add to delivering list if shipment is in transit; current status="
    //                         + shipment.getStatus().getValue());
    //     }

    //     this.getShipments().add(shipment);
    // }
}
