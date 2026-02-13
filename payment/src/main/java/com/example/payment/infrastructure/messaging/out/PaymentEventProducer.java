package com.example.payment.infrastructure.messaging.out;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.payment.infrastructure.messaging.event.OrderPaidEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentEventProducer {
    private final KafkaTemplate<String, OrderPaidEvent> kafkaTemplate;

    public void publishOrderPaid(OrderPaidEvent event) {
        kafkaTemplate.send(
            "order.paid",
            event.orderId().toString(),
            event
        );
    }
}
