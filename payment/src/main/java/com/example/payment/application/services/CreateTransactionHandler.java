package com.example.payment.application.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.example.payment.application.commands.CreateTransactionCommand;
import com.example.payment.application.ports.out.OrderGateway;
import com.example.payment.application.ports.out.PaymentRepository;
import com.example.payment.application.ports.out.TransactionRepository;
import com.example.payment.domain.model.Payment;
import com.example.payment.domain.model.Transaction;
import com.example.payment.domain.vo.OrderSnapshot;
import com.example.payment.infrastructure.messaging.out.OrderGrpcGateway;
import com.example.payment.interfaces.rest.dto.TransactionResponseDto;
import com.example.payment.interfaces.rest.mapper.TransactionMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateTransactionHandler {
    private TransactionRepository repository;
    private PaymentRepository paymentRepository;
    private TransactionMapper mapper;

    private OrderGrpcGateway orderGateway;
    
    public TransactionResponseDto handle(CreateTransactionCommand cmd) {
        OrderSnapshot order = orderGateway.getOrder(cmd.orderId());

        if (order.getTotalCost().equals(BigDecimal.valueOf(-1))) {
            throw new EntityNotFoundException("Order not found");
        }

        Payment payment = paymentRepository
            .findById(cmd.paymentId())
            .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        Transaction transaction = Transaction.newTransaction(cmd.orderId(), payment);

        BigDecimal orderCost = order.getTotalCost();

        transaction.verifySufficientFunds(orderCost);

        transaction.subtractFromOrder(orderCost);

        Transaction savedTransaction = repository.save(transaction);

        // shoot kafak to order(dispatch status) and delivery

        return mapper.toDto(savedTransaction);
    }
}
