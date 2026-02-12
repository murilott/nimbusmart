package com.example.payment.interfaces;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.payment.application.commands.CreateTransactionCommand;
import com.example.payment.application.services.CreateTransactionHandler;
import com.example.payment.application.services.ListTransactionHandler;
import com.example.payment.interfaces.rest.dto.TransactionCreationDto;
import com.example.payment.interfaces.rest.dto.TransactionResponseDto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/transaction")
public class TransactionController {
    private CreateTransactionHandler createTransactionHandler;
    private ListTransactionHandler listTransactionHandler;

    @GetMapping("/hello")
    public String hello() {
        return "Hello Transaction!";
    }

    @GetMapping()
    public ResponseEntity<List<TransactionResponseDto>> listAll() {
        return ResponseEntity.ok(listTransactionHandler.handle());
    }
    

    @PostMapping()
    public ResponseEntity<TransactionResponseDto> create(@Valid @RequestBody TransactionCreationDto dto) {
        CreateTransactionCommand cmd = new CreateTransactionCommand(
            // dto.orderId(), // TODO: remove when grpc is implemented 
            UUID.randomUUID(), 
            dto.paymentId()
        );

        TransactionResponseDto created = createTransactionHandler.handle(cmd);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
