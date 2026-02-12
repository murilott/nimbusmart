package com.example.payment.application.commands;

import java.math.BigDecimal;

import com.example.payment.domain.vo.Method;
import com.example.payment.domain.vo.MethodType;

public record CreatePaymentCommand(
    String name, 
    BigDecimal funds, 
    BigDecimal creditLimit, 
    MethodType method
) {
    
}
