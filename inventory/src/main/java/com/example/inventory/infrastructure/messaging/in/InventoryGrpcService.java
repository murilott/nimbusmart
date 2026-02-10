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
import com.example.inventory.grpc.ReserveItemRequest;
import com.example.inventory.grpc.ReserveItemResponse;
import com.google.type.Money;

import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
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

    @Transactional
    @Override
    public void reserveItem(
            ReserveItemRequest request,
            StreamObserver<ReserveItemResponse> responseObserver) {

        log.info("Attempting to reserve item {} with quantity {}", 
            request.getInventoryItemId(), 
            request.getRequestedQuantity()
        );

        InventoryItem item = itemRepository
            .findById(UUID.fromString(request.getInventoryItemId()))
            .orElseThrow(() -> new EntityNotFoundException("Inventory Item not found"));

        int requestedQuantity = request.getRequestedQuantity();

        item.subtractInventoryItemQuantity(requestedQuantity);

        itemRepository.save(item);

        ReserveItemResponse response = ReserveItemResponse
                .newBuilder()
                .setOk(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    
}
