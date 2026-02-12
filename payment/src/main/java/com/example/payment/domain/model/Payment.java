package com.example.payment.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.payment.domain.vo.Method;
import com.example.payment.domain.vo.MethodType;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
public class Payment {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;
    
    @NotBlank(message = "Name must not be blank")
    private String name;

    @Positive(message = "Funds must not be negative")
    private BigDecimal funds;
    @Positive(message = "Limit must not be negative")
    private BigDecimal limit;
    @Positive(message = "LimitSpent must not be negative")
    private BigDecimal limitSpent;

    @Embedded
    private Method method;

    public static Payment newPayment(String name, BigDecimal funds, BigDecimal limit, MethodType methodType) {
        Payment payment = new Payment(name, funds, limit, methodType);

        return payment;
    }

    private Payment(String name, BigDecimal funds, BigDecimal limit, MethodType methodType) {
        this.setId(UUID.randomUUID());
        this.setName(name);
        this.setFunds(funds);
        this.setLimit(limit);
        this.setLimitSpent(BigDecimal.ZERO);
        this.setMethod(Method.of(methodType));
    }

    public void subtractFromOrder(BigDecimal orderCost) {
        if (orderCost.signum() == -1) {
            throw new IllegalArgumentException("Order cost to subtract must be greater than 0");
        }

        if (this.getMethod().getValue() == MethodType.CREDIT) {
            this.setLimitSpent(this.getLimitSpent().add(orderCost));

            return;
        }

        if (this.getMethod().getValue() == MethodType.DEBIT ||
            this.getMethod().getValue() == MethodType.PIX
        ) {
            BigDecimal finalFunds = this.getFunds().subtract(orderCost);
            this.setFunds(finalFunds);

            return;
        }
        
        throw new EntityNotFoundException("Payment method not found. Method=" + this.getMethod().getValue());
    }

    public void verifySufficientFunds(BigDecimal orderCost){
        if (orderCost.signum() == -1) {
            throw new IllegalArgumentException("Order cost to verify must be greater than 0");
        }
        
        if (this.getMethod().getValue() == MethodType.DEBIT ||
            this.getMethod().getValue() == MethodType.PIX
        ) {
            BigDecimal finalFunds = this.getFunds().subtract(orderCost);
            
            if (finalFunds.signum() == -1) {
                throw new IllegalArgumentException("Insufficient funds");
            }
        }
    }

    public void addFunds(BigDecimal newFunds) {
        if (newFunds.signum() == -1) {
            throw new IllegalArgumentException("New funds cannot have negative value");
        }

        BigDecimal finalFunds = this.getFunds().add(newFunds);
    
        this.setFunds(finalFunds);

    }

    public void newLimit(BigDecimal newLimit) {
        if (newLimit.signum() == -1) {
            throw new IllegalArgumentException("New Limit cannot have negative value");
        }
        
        this.setFunds(funds);
    }

    private void setFunds(BigDecimal funds) {
        if (funds.signum() == -1) {
            throw new IllegalArgumentException("Funds cannot have negative value");
        }
        
        this.setFunds(funds);
    }
}
