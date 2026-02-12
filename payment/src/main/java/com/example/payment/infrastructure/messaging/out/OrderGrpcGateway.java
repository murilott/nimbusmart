package com.example.payment.infrastructure.messaging.out;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.order.grpc.GetOrderRequest;
import com.example.order.grpc.GetOrderResponse;
import com.example.order.grpc.OrderServiceGrpc;
import com.example.payment.application.ports.out.OrderGateway;
import com.example.payment.domain.vo.OrderSnapshot;

import net.devh.boot.grpc.client.inject.GrpcClient;

@Component
public class OrderGrpcGateway implements OrderGateway{
    @GrpcClient("order")
    private OrderServiceGrpc.OrderServiceBlockingStub stub;

    @Override
    public OrderSnapshot getOrder(UUID orderId) {
        GetOrderRequest request = GetOrderRequest
                .newBuilder()
                .setOrderId(orderId.toString())
                .build();

        GetOrderResponse response = stub.getOrder(request);

        BigDecimal cost = new BigDecimal(response.getCost().getValue());

        OrderSnapshot order = OrderSnapshot.of(orderId, cost);
        return order;
    }
}
