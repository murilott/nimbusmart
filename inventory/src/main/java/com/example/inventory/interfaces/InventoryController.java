package com.example.inventory.interfaces;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventory.application.commands.CreateInventoryCommand;
import com.example.inventory.application.services.CreateInventoryHandler;
import com.example.inventory.application.services.ListInventoryHandler;
import com.example.inventory.interfaces.rest.dto.inventory.InventoryCreationDto;
import com.example.inventory.interfaces.rest.dto.inventory.InventoryResponseDto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {
    private final ListInventoryHandler listInventoryHandler;
    private final CreateInventoryHandler CreateInventoryHandler;

    @GetMapping
    public ResponseEntity<List<InventoryResponseDto>> listAll() {
        List<InventoryResponseDto> items = listInventoryHandler.handle();
        return ResponseEntity.ok(items);
    }

    @PostMapping()
    public ResponseEntity<InventoryResponseDto> create(@Valid @RequestBody InventoryCreationDto request) {
        CreateInventoryCommand command = new CreateInventoryCommand(request.location(), request.name());
        
        InventoryResponseDto created = CreateInventoryHandler.handle(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
