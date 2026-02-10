package com.example.order.application.ports.out;

import java.util.UUID;

import com.example.order.domain.vo.ItemSnapshot;

public interface InventoryGateway {
    ItemSnapshot checkAvailability(UUID inventoryItemId, int requestedQuantity);
    boolean reserveItem(UUID inventoryItemId, int requestedQuantity);
}
