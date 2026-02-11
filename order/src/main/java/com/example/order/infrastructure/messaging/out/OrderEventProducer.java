package com.example.order.infrastructure.messaging.out;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderCanceledEvent> kafkaTemplate;

    public void publishOrderCanceled(OrderCanceledEvent event) {
        kafkaTemplate.send(
            "order.events",
            event.orderId().toString(),
            event
        );
    }
}
