package com.example.inventory.infrastructure.messaging.out;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.inventory.application.ports.out.ProductGateway;
import com.example.product.grpc.ProductExistsRequest;
import com.example.product.grpc.ProductExistsResponse;
import com.example.product.grpc.ProductServiceGrpc;

import net.devh.boot.grpc.client.inject.GrpcClient;

@Component
public class ProductGrpcGateway implements ProductGateway {

    @GrpcClient("product")
    private ProductServiceGrpc.ProductServiceBlockingStub stub;

    @Override
    public boolean exists(UUID productId) {
        ProductExistsRequest request = ProductExistsRequest
                .newBuilder()
                .setProductId(productId.toString())
                .build();

        ProductExistsResponse response = stub.exists(request);
        return response.getExists();
    }
}
