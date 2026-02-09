package com.example.order.application.ports.out;

import java.util.UUID;

import com.example.order.domain.vo.ItemSnapshot;

public interface InventoryGateway {
    ItemSnapshot exists(UUID inventoryItemId);
}
