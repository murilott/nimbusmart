import type BigNumber from "bignumber.js"
import type { OrderItemDto } from "./OrderItemDto"
import type { OrderStatus } from "../enums/OrderStatus"

export type OrderDto = {
    id: number | null,
    items: OrderItemDto[],
    createdAt: Date,

    confirmedAt: Date | null,
    dispatchedAt: Date | null,
    deliveredAt: Date | null,
    cancelledAt: Date | null,

    totalCost: BigNumber,

    status: OrderStatus,
}