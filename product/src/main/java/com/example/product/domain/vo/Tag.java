package com.example.product.domain.vo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Embeddable
@Getter
public class Tag {
    @Column(name = "tags")
    private List<String> value = new ArrayList<>();

    // TODO: methods to remove vo tags
    // TODO: turn ifs into method to validate string value

    public void addTag(String rawValue) {
        if (rawValue == null) {
            log.info("Tag not informed");
            return;
        }

        String value = rawValue.trim().toLowerCase();

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Tag must not be blank");
        }

        if (this.value.contains(value)) {
            throw new IllegalArgumentException("Tag is already on the list");
        }

        this.getValue().add(value);
    }

    public void addTags(List<String> rawListValue) {
        if (rawListValue == null) {
            log.info("Tags not informed");
            return;
        }

        List<String> values = rawListValue.stream().map(rawValue -> {
            String value = rawValue.trim().toLowerCase();
    
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("Tag must not be blank");
            }
    
            if (this.value.contains(value)) {
                throw new IllegalArgumentException("Tag is already on the list");
            }

            return value;
        }).distinct().toList();

        this.getValue().addAll(values);
    }
}
