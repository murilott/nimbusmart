package com.example.payment.domain.vo;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Embeddable
@Getter
public class OrderSnapshot {
    @Column(nullable = false, updatable = false)
    private UUID orderId;
    @Positive(message = "CreditLimit must be greater than 0")
    private BigDecimal totalCost;

    public static OrderSnapshot of(UUID orderId, BigDecimal totalCost) {
        OrderSnapshot order = new OrderSnapshot(orderId, totalCost);

        return order;
    }

    private OrderSnapshot(UUID orderId, BigDecimal totalCost) {
        this.orderId = orderId;
        this.totalCost = totalCost;
    }
}
