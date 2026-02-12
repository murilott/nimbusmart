package com.example.payment.infrastructure.persistance.payment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.payment.application.ports.out.PaymentRepository;
import com.example.payment.application.ports.out.PaymentRepository;
import com.example.payment.domain.model.Payment;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository{
    private final PaymentRepositoryJpa jpa;

    @Override
    public Optional<Payment> findById(UUID id) {
        return jpa.findById(id);
    }

    @Override
    public List<Payment> findAll() {
        return jpa.findAll();
    }

    @Override
    public Page<Payment> findAll(Pageable pageable) {
        return jpa.findAll(pageable);
    }

    @Override
    public Payment save(Payment payment) {
        return jpa.save(payment);
    }
}
