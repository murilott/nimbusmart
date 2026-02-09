package com.example.order.domain.vo;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class ItemSnapshot {

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "item_cost", nullable = false, precision = 19, scale = 2)
    private BigDecimal cost;

    public static ItemSnapshot of(Long itemId, BigDecimal cost) {
        ItemSnapshot item = new ItemSnapshot(itemId, cost);

        return item;
    }

    public ItemSnapshot(Long itemId, BigDecimal cost) {
        this.itemId = itemId;
        this.cost = cost;
    }
}
