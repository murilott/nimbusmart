package com.example.payment.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;
    
    @Column(nullable = false, updatable = false)
    private UUID orderId;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private OffsetDateTime createdAt;

    public static Transaction newTransaction(UUID orderId, Payment payment) {
        Transaction transaction = new Transaction(orderId, payment);

        return transaction;
    }

    private Transaction(UUID orderId, Payment payment) {
        this.setId(UUID.randomUUID());
        this.setOrderId(orderId);
        this.setPayment(payment);
        this.setCreatedAt(OffsetDateTime.now());
    }

    public void subtractFromOrder(BigDecimal orderCost) {
        this.getPayment().subtractFromOrder(orderCost);
    }

    public void verifySufficientFunds(BigDecimal orderCost){
        this.getPayment().verifySufficientFunds(orderCost);
    }
}
