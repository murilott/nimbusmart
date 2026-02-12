package com.example.payment.interfaces.rest.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

import com.example.payment.domain.model.Payment;

public record TransactionResponseDto(
    UUID id,
    UUID orderId,
    Payment payment,
    OffsetDateTime createdAt
) {
    
}
