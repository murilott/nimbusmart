package com.example.payment.application.commands;

import java.util.UUID;

import com.example.payment.domain.model.Payment;

public record CreateTransactionCommand(
    UUID orderId,
    UUID paymentId
) {
    
}
