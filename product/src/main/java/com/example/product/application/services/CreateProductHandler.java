package com.example.product.application.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.product.application.commands.CreateProductCommand;
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


    public ProductResponseDto handle(CreateProductCommand dto) {
        List<String> tagList = Arrays.stream(dto.tags().split(","))
            .map(item -> item.trim().toLowerCase())
            .toList();
                
        Product product = Product.newProduct(dto.name(), dto.description(), tagList, dto.image());
        Product savedProduct = repository.save(product);
        
        return mapper.toDto(savedProduct);
    }
    
}
