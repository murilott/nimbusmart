package com.example.product.interfaces.rest.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record ProductCreationDto(
    @NotBlank(message = "Title must not be blank")
    String title,
    @NotBlank(message = "Description must not be blank")
    String description,
    
    List<String> tags
) {
    
}
