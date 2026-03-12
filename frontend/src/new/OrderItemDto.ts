import BigNumber from "bignumber.js";
import type { OrderItemDto } from "../types/OrderItemDto";

export const OrderItemNew: OrderItemDto = {
    id: null,
    itemId: null,
    itemCost: new BigNumber(0),
    cost:  new BigNumber(0),
    quantity: 1,
}