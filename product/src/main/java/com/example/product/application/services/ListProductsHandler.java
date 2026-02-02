package com.example.product.application.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.product.application.ports.out.ProductRepository;
import com.example.product.domain.model.Product;
import com.example.product.interfaces.rest.dto.ProductResponseDto;
import com.example.product.interfaces.rest.mapper.ProductMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ListProductsHandler {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    public List<ProductResponseDto> handle() {
        List<Product> list = repository.findAll();
        return mapper.toDtoList(list);
    }
    
    public Page<ProductResponseDto> handle(Pageable pageable) {
        Page<Product> page = repository.findAll(pageable);
        
        return page.map(product -> mapper.toDto(product));
    }
}
