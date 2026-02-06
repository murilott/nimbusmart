package com.example.order.domain.vo;

import lombok.AccessLevel;
import lombok.Getter;

@Getter(AccessLevel.PRIVATE)
public enum StatusType {
    PENDING(1),
    CONFIRMED(2),
    DISPATCHED(3),
    DELIVERED(4),
    CANCELLED(5);

    private int type;

    private StatusType(int type) {
        this.type = type;
    }

    // private StatusType typeToStatus(int type) {
    //     return StatusType.CONFIRMED.getType();
    // }

    // public StatusType elevate(StatusType type) {
    //     return type. + 1;
    // }
}
