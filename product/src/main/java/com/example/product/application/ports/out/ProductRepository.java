package com.example.product.application.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.product.domain.model.Product;

public interface ProductRepository {
    public Product save(Product product);
    public List<Product> findAll();
    public Page<Product> findAll(Pageable pageable);
    public Optional<Product> findById(UUID id);
    public boolean existsById(UUID id);
}
