package com.example.product.infrastructure.messaging.in;


import java.util.UUID;

import com.example.product.application.ports.out.ProductRepository;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import com.example.product.grpc.ProductExistsRequest;
import com.example.product.grpc.ProductExistsResponse;
import com.example.product.grpc.ProductServiceGrpc;

@AllArgsConstructor
@GrpcService
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductRepository repository;

    @Override
    public void exists(
            ProductExistsRequest request,
            StreamObserver<ProductExistsResponse> responseObserver) {

        boolean exists = repository.existsById(UUID.fromString(request.getProductId()));

        ProductExistsResponse response = ProductExistsResponse
                .newBuilder()
                .setExists(exists)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
