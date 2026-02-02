package com.example.product.domain.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.example.product.domain.vo.Tag;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
// @NoArgsConstructor
// @RequiredArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
public class Product {
    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    private String title;
    private String description;
    private String image;

    @Embedded
    private Tag tags = new Tag();

    private OffsetDateTime createdAt;

    private boolean archived;
    private OffsetDateTime archivedAt;

    public static Product newProduct(String title, String description, List<String> tags) {
        Product prod = new Product();
        prod.setId(UUID.randomUUID());
        prod.setTitle(title);
        prod.setDescription(description);
        prod.addTag(tags);
        prod.setCreatedAt(OffsetDateTime.now());
        prod.setArchived(false);

        return prod;
    }

    public void addTag(String value) {
        this.getTags().addTag(value);
    }

    public void addTag(List<String> values) {
        this.getTags().addTags(values);
    }

    public void archive() {
        if (!this.isArchived()) {
            this.setArchived(true);
            this.setArchivedAt(OffsetDateTime.now());
            log.info("Product {} archived at {}", this.getTitle(), this.getArchivedAt());
        } else {
            throw new RuntimeException("This product is already archived");
        }
    }
    
    public void restore() {
        if (this.isArchived()) {
            this.setArchived(false);
            this.setArchivedAt(null);
            log.info("Product {} restored at {}", this.getTitle(), this.getArchivedAt());
        } else {
            throw new RuntimeException("This product is already active");
        }
    }
}
