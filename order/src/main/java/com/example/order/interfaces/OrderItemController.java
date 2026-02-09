package com.example.order.interfaces;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.order.interfaces.rest.dto.OrderItemCreationDto;
import com.example.order.interfaces.rest.dto.OrderItemResponseDto;
import com.example.order.interfaces.rest.dto.OrderResponseDto;
import com.example.order.application.commands.CreateOrderItemCommand;
import com.example.order.application.services.CreateOrderHandler;
import com.example.order.application.services.ListOrderHandler;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v1/order-item")
@AllArgsConstructor
public class OrderItemController {
    private final ListOrderHandler listOrderHandler;
    private final com.example.order.application.services.CreateOrderItemHandler createOrderItemHandler;
    
    @GetMapping
    public ResponseEntity<List<OrderItemResponseDto>> listAll() {
        List<OrderItemResponseDto> items = listOrderHandler.itemsHandler();
        return ResponseEntity.ok(items);
    }

    @PostMapping()
    public ResponseEntity<OrderItemResponseDto> create(@Valid @RequestBody OrderItemCreationDto request) {                
        CreateOrderItemCommand command = new CreateOrderItemCommand(
            request.orderId(), 
            request.inventoryItemId(), 
            request.quantity()
        );
        
        OrderItemResponseDto created = createOrderItemHandler.handle(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
