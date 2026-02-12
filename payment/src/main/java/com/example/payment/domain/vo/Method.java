package com.example.payment.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@Embeddable
@Table(name = "methods")
public class Method {
    @Enumerated(EnumType.STRING)
    @Column(name = "payment", nullable = false, length = 20)
    private MethodType value;

    public static Method of(MethodType value) {
        return new Method(value);
    }

    private Method(MethodType value) {
        this.value = value;
    }
}
