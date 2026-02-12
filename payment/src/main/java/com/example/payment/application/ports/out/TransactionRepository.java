package com.example.payment.application.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.payment.domain.model.Transaction;

public interface TransactionRepository {
    public Transaction save(Transaction transaction);
    public List<Transaction> findAll();
    public Page<Transaction> findAll(Pageable pageable);
    public Optional<Transaction> findById(UUID id);
}
