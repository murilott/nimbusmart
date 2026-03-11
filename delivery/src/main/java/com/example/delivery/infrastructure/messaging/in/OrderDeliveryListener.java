package com.example.delivery.infrastructure.messaging.in;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.contracts.events.OrderDeliveryReadyEvent;
import com.example.delivery.application.services.NewDeliveryHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderDeliveryListener {
    private final NewDeliveryHandler newDeliveryHandler;

    // TODO: implement idempotency prevention
    @KafkaListener(topics = "order.delivery")
    public void onOrderPaid(OrderDeliveryReadyEvent event) {
        log.info("EVENT RECEIVED {}", event);
        
        newDeliveryHandler.handle(event.orderId());
    }
}