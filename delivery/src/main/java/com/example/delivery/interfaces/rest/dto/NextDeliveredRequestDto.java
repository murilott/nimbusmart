package com.example.delivery.interfaces.rest.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;


public record NextDeliveredRequestDto(
    @NotNull(message = "id must not be null")
    UUID id
) {
    
}
