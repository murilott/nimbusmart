package com.example.payment.interfaces.rest.dto;

import java.util.UUID;

import com.example.payment.domain.model.Payment;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransactionCreationDto(
    // @Column(nullable = false, updatable = false) // TODO: remove when grpc is implemented
    UUID orderId,
    @Column(nullable = false, updatable = false)
    UUID paymentId
) {
    
}
