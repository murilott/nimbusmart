package com.example.payment.application.services;

import org.springframework.stereotype.Service;

import com.example.payment.application.commands.CreatePaymentCommand;
import com.example.payment.application.ports.out.PaymentRepository;
import com.example.payment.domain.model.Payment;
import com.example.payment.interfaces.rest.dto.PaymentResponseDto;
import com.example.payment.interfaces.rest.mapper.PaymentMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreatePaymentHandler {
    private PaymentRepository repository;
    private PaymentMapper mapper;
    
    public PaymentResponseDto handle(CreatePaymentCommand cmd) {
        Payment payment = Payment.newPayment(cmd.name(), cmd.funds(), cmd.creditLimit(), cmd.method());

        Payment savedPayment = repository.save(payment);

        return mapper.toDto(savedPayment);
    }
}
