package com.example.order.domain.model.order;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.order.domain.vo.ItemSnapshot;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

    private Order order;

    @Embedded
    private ItemSnapshot inventoryItem;

    @Positive(message = "Order quantity must be greater than 0")
    private int quantity;

    private BigDecimal cost;

    public static OrderItem newOrderItem(ItemSnapshot InventoryItem, int quantity) {
        OrderItem item = new OrderItem(InventoryItem, quantity);

        return item;
    }

    private OrderItem(ItemSnapshot InventoryItem, int quantity) {
        this.setId(UUID.randomUUID());
        this.setInventoryItem(InventoryItem);
        this.setQuantity(quantity);
        this.setCost(calculateCost());
    }

    private BigDecimal calculateCost() {
        return this.getInventoryItem().getCost().multiply(BigDecimal.valueOf(this.getQuantity()));
    }
}
