package com.example.inventory.domain.model.inventory;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.inventory.domain.vo.Status;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
public class Inventory {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @OneToMany(
        mappedBy = "inventory",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<InventoryItem> items = new ArrayList<>();

    private OffsetDateTime createdAt;

    // TODO: location and name trim()

    @NotBlank(message = "Location must not be blank")
    private String location;

    @NotBlank(message = "Name must not be blank")
    private String name;
    
    // @Embedded
    // private Status status;

    public static Inventory newInventory(String location, String name) {
        Inventory inv = new Inventory();
        inv.setId(UUID.randomUUID());
        inv.setLocation(location);
        inv.setName(name);
        inv.setCreatedAt(OffsetDateTime.now());

        return inv;
    }

    public void addToInventory(InventoryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("InventoryItem to be added is null");
        }
        
        this.getItems().add(item);
    }
    
    public void addToInventory(List<InventoryItem> items) {
        if (items == null) {
            throw new IllegalArgumentException("InventoryItem List to be added is null");
        }

        this.getItems().addAll(items);
    }
}
