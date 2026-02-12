package com.example.payment.infrastructure.persistance.transaction;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payment.domain.model.Transaction;

public interface TransactionRepositoryJpa extends JpaRepository<Transaction, UUID>{
    
}
