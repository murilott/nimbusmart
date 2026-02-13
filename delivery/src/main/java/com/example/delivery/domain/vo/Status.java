package com.example.delivery.domain.vo;

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
            case StatusType.PENDING:
                this.setValue(StatusType.IN_TRANSIT);
                break;
        
            case StatusType.IN_TRANSIT:
                this.setValue(StatusType.DELIVERED);        
                break;

            case StatusType.FAILED:
                throw new IllegalArgumentException("Shipment is failed, cannot elevate");
        
            case StatusType.DELIVERED:
                throw new IllegalArgumentException("Shipment is delivered, cannot elevate");
                
            default:
                throw new IllegalArgumentException("Cannot elevate Status");
        }

        return this;
    }
}
