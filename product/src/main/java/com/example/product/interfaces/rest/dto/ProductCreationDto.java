package com.example.product.interfaces.rest.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record ProductCreationDto(
    @NotBlank(message = "Name must not be blank")
    String name,
    @NotBlank(message = "Description must not be blank")
    String description,
    
    String image,
    String tags
) {
    
}
