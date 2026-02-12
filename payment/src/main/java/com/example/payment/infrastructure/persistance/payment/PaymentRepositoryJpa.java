package com.example.payment.infrastructure.persistance.payment;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.payment.domain.model.Payment;

public interface PaymentRepositoryJpa extends JpaRepository<Payment, UUID>{
    
}
