package com.example.order.application.commands;

import java.util.UUID;

public record CancelOrderCommand(
    UUID orderId
) {
    
}
