package com.example.payment.application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.payment.application.ports.out.TransactionRepository;
import com.example.payment.interfaces.rest.dto.TransactionResponseDto;
import com.example.payment.interfaces.rest.mapper.TransactionMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ListTransactionHandler {
    private TransactionRepository repository;
    private TransactionMapper mapper;
    
    public List<TransactionResponseDto> handle() {
        return mapper.toDtoList(repository.findAll());
    }
}
