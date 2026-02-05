package com.example.product.application.commands;

import java.util.List;

public record CreateProductCommand(
    String title,
    String description,
    List<String> tags
) {
    
}
