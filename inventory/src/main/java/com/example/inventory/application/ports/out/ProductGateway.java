package com.example.inventory.application.ports.out;

import java.util.UUID;

public interface ProductGateway {
    boolean exists(UUID productId);
}
