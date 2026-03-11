package com.example.delivery.infrastructure.messaging.out;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.contracts.events.OrderDeliveredEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeliveryEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishOrderDelivered(OrderDeliveredEvent event) {
        kafkaTemplate.send(
            "order.delivered",
            event.orderId().toString(),
            event
        );
    }
}
