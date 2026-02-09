package com.example.order.domain.model.order;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.example.order.domain.vo.Status;
import com.example.order.domain.vo.StatusType;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@Entity
public class Order {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    private List<OrderItem> items;

    private OffsetDateTime createdAt; // at new instance
    private OffsetDateTime confirmedAt; // once payment is confirmed
    private OffsetDateTime dispatchedAt; // order has left stock
    private OffsetDateTime deliveredAt; // when courier has delivered
    private OffsetDateTime cancelledAt;

    private BigDecimal totalCost;

    @Embedded
    private Status status;

    public static Order newOrder() {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setCreatedAt(OffsetDateTime.now());
        order.setStatus(Status.of(StatusType.PENDING));

        return order;
    }

    private Order() {
    }

    // public void elevateStatus() {
    //     this.setStatus(Status.of(this.getStatus().getValue()));
    // }

    public void addToOrder(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("OrderItem to be added is null");
        }
        
        this.getItems().add(item);

        calculateCost();
    }
    
    public void addToOrder(List<OrderItem> items) {
        if (items == null) {
            throw new IllegalArgumentException("OrderItem List to be added is null");
        }
        
        this.getItems().addAll(items);
        
        calculateCost();
    }

    private void nextStep() {
        this.getStatus().nextStatus();
    } 

    // private BigDecimal calculateCost(List<OrderItem> items) {
    //     BigDecimal costToAdd = BigDecimal.ZERO;
    // }
    // if (cost == null) {
    //     throw new IllegalArgumentException("Cost to add cannot be null");
    // }

    // if (cost.compareTo(BigDecimal.ZERO) < 0) {
    //     throw new IllegalArgumentException("Cost to add cannot be negative");
    // }

    private void calculateCost() {

        BigDecimal newTotalCost = BigDecimal.ZERO;

        this.getItems().stream().forEach(item -> {
            newTotalCost.add(item.getCost());
        });

        // TODO: maybe add negative or < 0 validation for newTotalCost

        this.setTotalCost(newTotalCost);
    }
}
