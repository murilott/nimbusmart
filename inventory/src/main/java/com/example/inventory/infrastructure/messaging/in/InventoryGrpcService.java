package com.example.inventory.infrastructure.messaging.in;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import com.example.inventory.application.ports.out.InventoryItemRepository;
import com.example.inventory.application.ports.out.InventoryRepository;
import com.example.inventory.domain.model.inventory.InventoryItem;
import com.example.inventory.domain.vo.StatusType;
import com.example.inventory.grpc.InventoryAvailabilityRequest;
import com.example.inventory.grpc.InventoryAvailabilityResponse;
import com.example.inventory.grpc.InventoryServiceGrpc;
import com.google.type.Money;

import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@AllArgsConstructor
public class InventoryGrpcService extends InventoryServiceGrpc.InventoryServiceImplBase {
    private final InventoryRepository repository;
    private final InventoryItemRepository itemRepository;

    @Override
    public void checkAvailability(
            InventoryAvailabilityRequest request,
            StreamObserver<InventoryAvailabilityResponse> responseObserver) {

        InventoryItem item = itemRepository
            .findById(UUID.fromString(request.getInventoryItemId()))
            .orElseThrow(() -> new EntityNotFoundException("Inventory Item not found"));

        // boolean available = itemRepository.existsById(UUID.fromString(request.getInventoryItemId()));
        boolean avaiable = item.getStatus().getValue() == StatusType.IN_STOCK;

        int availableQuantity = item.getQuantity() - request.getRequestedQuantity();

        if (availableQuantity < 0) {
            throw new IllegalArgumentException("Order quantity cannot exceed stock quantity");
        }

        BigDecimal cost = item.getPrice().setScale(9, RoundingMode.HALF_UP);

        long units = cost.longValue();
        
        BigDecimal nanosDecimal = cost.remainder(BigDecimal.ONE).movePointRight(9);
        int nanos = nanosDecimal.intValueExact(); // intValueExact checks for overflow.

        Money moneyCost = Money.newBuilder()
                .setUnits(units)
                .setNanos(nanos)
                .build();

        InventoryAvailabilityResponse response = InventoryAvailabilityResponse
                .newBuilder()
                .setAvailable(avaiable)
                .setAvailableQuantity(availableQuantity)
                .setCost(moneyCost)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
