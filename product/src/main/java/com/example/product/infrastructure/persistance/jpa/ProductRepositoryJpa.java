package com.example.product.infrastructure.persistance.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.product.domain.model.Product;

public interface ProductRepositoryJpa extends JpaRepository<Product, UUID>{
    
}
