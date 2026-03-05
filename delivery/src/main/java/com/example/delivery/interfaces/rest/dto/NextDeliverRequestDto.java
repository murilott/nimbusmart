package com.example.delivery.interfaces.rest.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record NextDeliverRequestDto(
    @NotNull(message = "id must not be null")
    UUID id,
    @Positive(message = "days must be greater than 0")
    int days
) {
    
}
