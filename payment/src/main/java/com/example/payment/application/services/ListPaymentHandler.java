package com.example.payment.application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.payment.application.ports.out.PaymentRepository;
import com.example.payment.interfaces.rest.dto.PaymentResponseDto;
import com.example.payment.interfaces.rest.mapper.PaymentMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ListPaymentHandler {
    private PaymentRepository repository;
    private PaymentMapper mapper;
    
    public List<PaymentResponseDto> handle() {
        return mapper.toDtoList(repository.findAll());
    }
}
