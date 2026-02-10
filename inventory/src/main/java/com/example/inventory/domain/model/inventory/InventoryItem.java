package com.example.inventory.domain.model.inventory;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.example.inventory.domain.vo.Status;
import com.example.inventory.domain.vo.StatusType;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
public class InventoryItem {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private UUID productId;

    @Version
    @Column(nullable = false)
    private Long version;

    @ManyToOne(optional = false)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @PositiveOrZero(message = "Quantity must not be negative")
    private int quantity;

    @PositiveOrZero(message = "Price must not be negative")
    private BigDecimal price;

    private OffsetDateTime createdAt;

    @Embedded
    private Status status;

    public static InventoryItem newItem(UUID productId, Inventory inventory, int quantity, BigDecimal price) {
        if (productId == null) {
            throw new IllegalArgumentException("InventoryItem must be associated with a product id");
        }

        if (inventory == null) {
            throw new IllegalArgumentException("InventoryItem must be associated with an Inventory");
        }

        InventoryItem item = new InventoryItem(productId, inventory, quantity, price);

        return item;
    }

    private InventoryItem(UUID productId, Inventory inventory, int quantity, BigDecimal price) {
        this.setId(UUID.randomUUID());
        this.setProductId(productId);
        this.setInventory(inventory);
        this.setQuantity(quantity);
        this.setPrice(price);
        // this.setStatus(Status.of(StatusType.IN_STOCK));
        this.setCreatedAt(OffsetDateTime.now());
    }

    public void subtractInventoryItemQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to subtract must be greater than 0");
        }
        
        int finalQuantity = this.quantity - quantity;

        if (finalQuantity < 0) {
            throw new IllegalArgumentException("Quantity to subtract cannot exceed Inventory quantity");
        }

        this.setQuantity(finalQuantity);
    }

    public void addInventoryItemQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to add must be greater than 0");
        }

        int finalQuantity = this.quantity + quantity;

        this.setQuantity(finalQuantity);
    }

    public void changePrice(BigDecimal newPrice) {
        if (newPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        this.setPrice(newPrice);
    }

    private void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
            this.setStatus(Status.of(StatusType.IN_STOCK));
            return;
        }
        
        if (quantity == 0) {
            this.quantity = quantity;
            this.setStatus(Status.of(StatusType.OUT_OF_STOCK));
            return;
        }

        throw new IllegalArgumentException("InventoryItem quantity cannot have negative values");
    }
}
