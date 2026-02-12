package com.example.payment.interfaces.rest.dto;

import java.math.BigDecimal;

import com.example.payment.domain.vo.MethodType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record PaymentCreationDto(
    @NotBlank(message = "Name must not be blank")
    String name, 
    @PositiveOrZero(message = "Funds must not be negative")
    BigDecimal funds, 
    @PositiveOrZero(message = "Limit must not be negative")
    BigDecimal creditLimit, 
    @NotNull(message = "Payment Method Type must not be null")
    MethodType method
) {
    
}
