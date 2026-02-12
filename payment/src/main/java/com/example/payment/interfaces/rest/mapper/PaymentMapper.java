package com.example.payment.interfaces.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.payment.domain.model.Payment;
import com.example.payment.interfaces.rest.dto.PaymentResponseDto;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentResponseDto toDto(Payment payment);
    List<PaymentResponseDto> toDtoList(List<Payment> payments);
}
