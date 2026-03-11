package com.example.order.infrastructure.messaging.out;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.contracts.events.OrderCanceledEvent;
import com.example.contracts.events.OrderDeliveryReadyEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishOrderCanceled(OrderCanceledEvent event) {
        kafkaTemplate.send(
            "order.cancel",
            event.orderId().toString(),
            event
        );
    }

    public void publishOrderDeliveryReady(OrderDeliveryReadyEvent event) {
        kafkaTemplate.send(
            "order.delivery",
            event.orderId().toString(),
            event
        );
    }
}
