package com.example.product.interfaces;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.application.commands.CreateProductCommand;
import com.example.product.application.services.CreateProductHandler;
import com.example.product.application.services.ListProductsHandler;
import com.example.product.interfaces.rest.dto.ProductCreationDto;
import com.example.product.interfaces.rest.dto.ProductResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ListProductsHandler listProductsHandler;
    private final CreateProductHandler createProductHandler;

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> list(Pageable pageable) {
        Page<ProductResponseDto> page = listProductsHandler.handle(pageable);

        return ResponseEntity.ok(page);
    }

    @PostMapping()
    public ResponseEntity<ProductResponseDto> create(@Valid @RequestBody ProductCreationDto request) {
        CreateProductCommand command = new CreateProductCommand(
            request.title(), 
            request.description(), 
            request.tags()
        );
        ProductResponseDto created = createProductHandler.handle(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
