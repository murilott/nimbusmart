package com.example.inventory.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Embeddable
@Getter
public class Status {
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusType value;

    public static Status of(StatusType value) {
        return new Status(value);
    }

    private Status(StatusType value) {
        this.value = value;
    }
}
