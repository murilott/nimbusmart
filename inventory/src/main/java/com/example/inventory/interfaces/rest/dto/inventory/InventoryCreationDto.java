package com.example.inventory.interfaces.rest.dto.inventory;

import jakarta.validation.constraints.NotBlank;

public record InventoryCreationDto(
    @NotBlank(message = "Location must not be blank")
    String location,
    @NotBlank(message = "Name must not be blank")
    String name
) {
    
}
