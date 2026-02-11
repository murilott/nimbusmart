package com.example.order.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@Embeddable
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

    public Status nextStatus() {
        switch (this.getValue()) {
            case PENDING:
                this.setValue(StatusType.CONFIRMED);
        
            case CONFIRMED:
                this.setValue(StatusType.DISPATCHED);        
        
            case DISPATCHED:
                this.setValue(StatusType.DELIVERED);
        
            case CANCELLED:
                throw new IllegalArgumentException("Order is canceled, cannot elevate");
        
            case DELIVERED:
                throw new IllegalArgumentException("Order is delivered, cannot elevate");
        
            default:
                throw new IllegalArgumentException("Cannot elevate Status");
        }
    }
}
