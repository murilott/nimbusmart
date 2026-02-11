package com.example.order.domain.model.order;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.order.domain.vo.ItemSnapshot;
import com.example.order.domain.vo.StatusType;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
public class OrderItem {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    @Embedded
    private ItemSnapshot inventoryItem;

    @Positive(message = "Order quantity must be greater than 0")
    private int quantity;

    private BigDecimal cost;

    public static OrderItem newOrderItem(Order order, ItemSnapshot InventoryItem, int quantity) {
        if (!order.getStatus().getValue().equals(StatusType.PENDING)) {
            throw new IllegalArgumentException(
                "Can only create OrderItem with Order status pending; current status=" 
                + order.getStatus().getValue()
            );
        }

        OrderItem item = new OrderItem(order, InventoryItem, quantity);

        return item;
    }

    private OrderItem(Order order, ItemSnapshot InventoryItem, int quantity) {
        this.setId(UUID.randomUUID());
        this.setOrder(order);
        this.setInventoryItem(InventoryItem);
        this.setQuantity(quantity);
        this.setCost(calculateCost());
    }

    private BigDecimal calculateCost() {
        return this.getInventoryItem().getCost().multiply(BigDecimal.valueOf(this.getQuantity()));
    }
}
