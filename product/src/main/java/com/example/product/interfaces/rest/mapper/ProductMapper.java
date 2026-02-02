package com.example.product.interfaces.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.product.domain.model.Product;
import com.example.product.interfaces.rest.dto.ProductResponseDto;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDto toDto(Product product);
    List<ProductResponseDto> toDtoList(List<Product> product);
}
