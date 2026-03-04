package com.example.delivery.interfaces.rest.dto;

import java.util.UUID;


public record NextDeliverRequestDto(
    UUID id,
    int days
) {
    
}
