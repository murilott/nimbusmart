package com.example.payment.interfaces;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.payment.application.commands.CreatePaymentCommand;
import com.example.payment.application.services.CreatePaymentHandler;
import com.example.payment.application.services.ListPaymentHandler;
import com.example.payment.domain.model.Payment;
import com.example.payment.interfaces.rest.dto.PaymentCreationDto;
import com.example.payment.interfaces.rest.dto.PaymentResponseDto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {
    private CreatePaymentHandler createPaymentHandler;
    private ListPaymentHandler listPaymentHandler;

    @GetMapping("/hello")
    public String hello() {
        return "Hello Payment!";
    }

    @GetMapping()
    public ResponseEntity<List<PaymentResponseDto>> listAll() {
        return ResponseEntity.ok(listPaymentHandler.handle());
    }
    

    @PostMapping()
    public ResponseEntity<PaymentResponseDto> create(@Valid @RequestBody PaymentCreationDto dto) {
        CreatePaymentCommand cmd = new CreatePaymentCommand(
            dto.name(), 
            dto.funds(), 
            dto.creditLimit(), 
            dto.method()
        );

        PaymentResponseDto created = createPaymentHandler.handle(cmd);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
