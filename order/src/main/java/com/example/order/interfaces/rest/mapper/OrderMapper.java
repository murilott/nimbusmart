package com.example.order.interfaces.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.order.domain.model.order.Order;
import com.example.order.interfaces.rest.dto.OrderResponseDto;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponseDto toDto(Order order);
    List<OrderResponseDto> toDtoList(List<Order> order);
}
