package com.example.product.application.services;

import org.springframework.stereotype.Service;

import com.example.product.application.ports.out.ProductRepository;
import com.example.product.domain.model.Product;
import com.example.product.interfaces.rest.dto.ProductCreationDto;
import com.example.product.interfaces.rest.dto.ProductResponseDto;
import com.example.product.interfaces.rest.mapper.ProductMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CreateProductHandler {
    private final ProductRepository repository;
    private final ProductMapper mapper;


    public ProductResponseDto handle(ProductCreationDto dto) {
        Product product = Product.newProduct(dto.title(), dto.description(), dto.tags());
        Product savedProduct = repository.save(product);
        
        return mapper.toDto(savedProduct);
    }
    
}
