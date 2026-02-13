package com.example.order.infrastructure.messaging.in;

import java.util.Optional;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.order.application.ports.out.OrderRepository;
import com.example.order.application.services.ConfirmOrderHandler;
import com.example.order.domain.model.order.Order;
import com.example.order.infrastructure.messaging.event.OrderPaidEvent;
import com.example.order.interfaces.rest.mapper.OrderMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderPaidListener {
    private final OrderRepository repository;
    private final OrderMapper mapper;

    private final ConfirmOrderHandler confirmOrderHandler;

    // TODO: implement idempotency prevention
    @KafkaListener(topics = "order.paid")
    public void onOrderPaid(OrderPaidEvent event) {
        confirmOrderHandler.handle(event.orderId());
    }
}
