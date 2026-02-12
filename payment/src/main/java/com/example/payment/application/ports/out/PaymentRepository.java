package com.example.payment.application.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.payment.domain.model.Payment;

public interface PaymentRepository {
    public Payment save(Payment payment);
    public List<Payment> findAll();
    public Page<Payment> findAll(Pageable pageable);
    public Optional<Payment> findById(UUID id);
}
