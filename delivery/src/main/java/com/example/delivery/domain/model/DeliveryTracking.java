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

    public Optional<Shipment> cancelShipment(UUID shipmentId) {
        Optional<Shipment> shipment = getShipments().stream()
            .filter(s -> s.getId().equals(shipmentId))
            .findFirst();
        
        if (shipment.isEmpty()) {
            return Optional.empty();
        }

        shipment.get().cancelShipment();

        return shipment;
    }

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

    public List<Shipment> getItemsCanceled() {
        return shipments.stream()
                .filter(s -> s.getStatus().getValue() == StatusType.FAILED)
                .toList();
    }
}
