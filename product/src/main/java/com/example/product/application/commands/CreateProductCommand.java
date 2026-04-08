package com.example.product.application.commands;

import java.util.List;

public record CreateProductCommand(
    String name,
    String description,
    String image,
    String tags
) {
    
}
