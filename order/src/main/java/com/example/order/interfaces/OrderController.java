package com.example.order.interfaces;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.order.interfaces.rest.dto.CancelOrderRequest;
import com.example.order.interfaces.rest.dto.OrderItemCreationDto;
import com.example.order.interfaces.rest.dto.OrderResponseDto;
import com.example.order.application.services.CreateOrderHandler;
import com.example.order.application.commands.CancelOrderCommand;
import com.example.order.application.services.CancelOrderHandler;
import com.example.order.application.services.ListOrderHandler;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/v1/order")
@AllArgsConstructor
public class OrderController {
    private final ListOrderHandler listOrderHandler;
    private final CreateOrderHandler CreateOrderHandler;
    private final CancelOrderHandler CancelOrderHandler;
    
    @GetMapping("/hello")
    public String hello() {
        return "Hello order!";
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> listAll() {
        List<OrderResponseDto> items = listOrderHandler.handle();
        return ResponseEntity.ok(items);
    }

    @PostMapping()
    public ResponseEntity<OrderResponseDto> create() {        
        OrderResponseDto created = CreateOrderHandler.handle();

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancel(@Valid @RequestBody CancelOrderRequest request) {       
        CancelOrderCommand cmd = new CancelOrderCommand(request.orderId());

        CancelOrderHandler.handle(cmd);

        return ResponseEntity.ok().body("Order canceled successfully");
    }
}
