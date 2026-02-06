package com.example.product.infrastructure.persistance.jpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.product.application.ports.out.ProductRepository;
import com.example.product.domain.model.Product;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductRepositoryJpa jpa;

    @Override
    public Optional<Product> findById(UUID id) {
        return jpa.findById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpa.existsById(id);
    }

    @Override
    public List<Product> findAll() {
        return jpa.findAll();
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return jpa.findAll(pageable);
    }

    @Override
    public Product save(Product product) {
        return jpa.save(product);
    }
}
