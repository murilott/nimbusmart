package com.example.payment.interfaces.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.payment.domain.model.Transaction;
import com.example.payment.interfaces.rest.dto.TransactionResponseDto;

@Mapper(componentModel = "spring",
        uses = PaymentMapper.class
)
public interface TransactionMapper {
    TransactionResponseDto toDto(Transaction transaction);
    List<TransactionResponseDto> toDtoList(List<Transaction> transactions);
}
