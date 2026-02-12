package com.example.payment.infrastructure.persistance.transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.payment.application.ports.out.TransactionRepository;
import com.example.payment.domain.model.Transaction;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository{
    private final TransactionRepositoryJpa jpa;

    @Override
    public Optional<Transaction> findById(UUID id) {
        return jpa.findById(id);
    }

    @Override
    public List<Transaction> findAll() {
        return jpa.findAll();
    }

    @Override
    public Page<Transaction> findAll(Pageable pageable) {
        return jpa.findAll(pageable);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return jpa.save(transaction);
    }
}
