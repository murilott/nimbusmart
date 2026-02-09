package com.example.order.infrastructure.messaging.out;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.inventory.grpc.InventoryItemRequest;
import com.example.inventory.grpc.InventoryItemResponse;
import com.example.inventory.grpc.InventoryServiceGrpc;
import com.example.order.application.ports.out.InventoryGateway;
import com.example.order.domain.vo.ItemSnapshot;

import net.devh.boot.grpc.client.inject.GrpcClient;

@Component
public class InventoryGrpcGateway implements InventoryGateway {

    @GrpcClient("inventory")
    private InventoryServiceGrpc.InventoryServiceBlockingStub stub;

    @Override
    public ItemSnapshot exists(UUID inventoryItemId) {
        InventoryItemRequest request = InventoryItemRequest
                .newBuilder()
                .setInventoryItemId(inventoryItemId.toString())
                .build();

        InventoryItemResponse response = stub.exists(request);

        long units = response.getCost().getUnits();
        int nanos = response.getCost().getNanos();

        BigDecimal cost = 
            BigDecimal.valueOf(units)
            .add(BigDecimal.valueOf(nanos, 9))
            .setScale(9, RoundingMode.HALF_UP);; 
        
        return ItemSnapshot.of(inventoryItemId, cost);
    }
}
