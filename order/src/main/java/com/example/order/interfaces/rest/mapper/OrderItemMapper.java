package com.example.order.interfaces.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.order.domain.model.order.OrderItem;
import com.example.order.interfaces.rest.dto.OrderItemResponseDto;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemResponseDto toDto(OrderItem order);
    List<OrderItemResponseDto> toDtoList(List<OrderItem> order);
}
