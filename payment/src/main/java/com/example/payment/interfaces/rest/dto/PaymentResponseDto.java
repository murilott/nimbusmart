package com.example.payment.interfaces.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.payment.domain.vo.Method;
import com.example.payment.domain.vo.MethodType;

import jakarta.validation.constraints.NotNull;

public record PaymentResponseDto(
    UUID id,
    String name,
    BigDecimal funds,
    BigDecimal creditLimit,
    BigDecimal limitSpent,
    Method method
) {
    
}
