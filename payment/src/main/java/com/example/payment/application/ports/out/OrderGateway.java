package com.example.payment.application.ports.out;

import java.util.UUID;

import com.example.payment.domain.vo.OrderSnapshot;

public interface OrderGateway {
    OrderSnapshot getOrder(UUID orderId);
}
