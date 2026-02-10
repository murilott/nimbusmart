package com.example.order.infrastructure.messaging.out;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.inventory.grpc.InventoryAvailabilityRequest;
import com.example.inventory.grpc.InventoryAvailabilityResponse;
import com.example.inventory.grpc.InventoryServiceGrpc;
import com.example.inventory.grpc.ReserveItemRequest;
import com.example.inventory.grpc.ReserveItemResponse;
import com.example.order.application.ports.out.InventoryGateway;
import com.example.order.domain.vo.ItemSnapshot;

import net.devh.boot.grpc.client.inject.GrpcClient;

@Component
public class InventoryGrpcGateway implements InventoryGateway {

    @GrpcClient("inventory")
    private InventoryServiceGrpc.InventoryServiceBlockingStub stub;

    @Override
    public ItemSnapshot checkAvailability(UUID inventoryItemId, int requestedQuantity) {
        InventoryAvailabilityRequest request = InventoryAvailabilityRequest
                .newBuilder()
                .setInventoryItemId(inventoryItemId.toString())
                .setRequestedQuantity(requestedQuantity)
                .build();

        InventoryAvailabilityResponse response = stub.checkAvailability(request);

        int availableQuantity = response.getAvailableQuantity();

        boolean available = response.getAvailable();

        long units = response.getCost().getUnits();
        int nanos = response.getCost().getNanos();

        BigDecimal cost = 
            BigDecimal.valueOf(units)
            .add(BigDecimal.valueOf(nanos, 9))
            .setScale(9, RoundingMode.HALF_UP);; 

        // if needed, turn returning type into Map for all response values
        
        return ItemSnapshot.of(inventoryItemId, cost);
    }

    @Override
    public boolean reserveItem(UUID inventoryItemId, int requestedQuantity) {
        ReserveItemRequest request = ReserveItemRequest
                .newBuilder()
                .setInventoryItemId(inventoryItemId.toString())
                .setRequestedQuantity(requestedQuantity)
                .build();

        ReserveItemResponse response = stub.reserveItem(request);

        boolean status = response.getOk();
        
        return status;
    }
}
