import BigNumber from "bignumber.js";
import type { OrderDto } from "../types/OrderDto";

export const orderNew: OrderDto = {
    id: null,
    items: [],
    createdAt: new Date(),
    confirmedAt: null,
    dispatchedAt: null,
    deliveredAt: null,
    cancelledAt: null,
    totalCost: new BigNumber(0),
    status: "PENDING",
}