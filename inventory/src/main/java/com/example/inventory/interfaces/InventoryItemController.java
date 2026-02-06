package com.example.inventory.interfaces;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory.application.commands.CreateInventoryItemCommand;
import com.example.inventory.application.services.CreateInventoryItemHandler;
import com.example.inventory.application.services.ListInventoryHandler;
import com.example.inventory.interfaces.rest.dto.inventoryItem.InventoryItemCreationDto;
import com.example.inventory.interfaces.rest.dto.inventoryItem.InventoryItemResponseDto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/inventory-item")
public class InventoryItemController {
    private final ListInventoryHandler listInventoryHandler;
    private final CreateInventoryItemHandler createInventoryItemHandler;

    @GetMapping
    public ResponseEntity<List<InventoryItemResponseDto>> listAll() {
        List<InventoryItemResponseDto> items = listInventoryHandler.itemsHandler();
        return ResponseEntity.ok(items);
    }

    @PostMapping()
    public ResponseEntity<InventoryItemResponseDto> create(@Valid @RequestBody InventoryItemCreationDto request) {                
        CreateInventoryItemCommand command = new CreateInventoryItemCommand(
            request.productId(), 
            request.inventoryId(), 
            request.quantity(),
            request.price()
        );
        
        InventoryItemResponseDto created = createInventoryItemHandler.handle(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
