package com.example.order.infrastructure.messaging.in;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import com.example.order.application.ports.out.OrderRepository;
import com.example.order.domain.model.order.Order;
import com.example.order.domain.vo.StatusType;
import com.example.order.grpc.GetOrderRequest;
import com.example.order.grpc.GetOrderResponse;
import com.example.order.grpc.OrderServiceGrpc;
import com.example.product.grpc.ProductServiceGrpc;
import com.google.type.Decimal;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@AllArgsConstructor
@GrpcService
public class OrderGrpcService extends OrderServiceGrpc.OrderServiceImplBase{

    private final OrderRepository repository;

    @Override
    public void getOrder(
            GetOrderRequest request,
            StreamObserver<GetOrderResponse> responseObserver) {

        Optional<Order> order = repository
                .findById(UUID.fromString(request.getOrderId()));

        Decimal totalCost = Decimal.newBuilder().setValue("-1").build();

        if (order.isEmpty()) {
            log.warn("Order {} not found. Ignoring event.",
                    request.getOrderId());
        }

        boolean invalid = order.isEmpty() || !order.get().getStatus().getValue().equals(StatusType.PENDING);
        
        if (!order.isEmpty()) {
            totalCost = Decimal.newBuilder().setValue(order.get().getTotalCost().toPlainString()).build();
        }

        GetOrderResponse response = GetOrderResponse
                .newBuilder()
                .setCost(totalCost)
                .setInvalid(invalid)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
