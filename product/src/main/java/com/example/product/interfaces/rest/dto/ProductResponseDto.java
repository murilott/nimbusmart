package com.example.product.interfaces.rest.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.example.product.domain.vo.Tag;

public record ProductResponseDto(
    UUID id,
    String title,
    String description,
    String image,
    Tag tags,
    OffsetDateTime createdAt,
    boolean archived,
    OffsetDateTime archivedAt
) {
}
