package com.example.inventory.application.commands;

public record CreateInventoryCommand(
    String location,
    String name
) {
    
}
