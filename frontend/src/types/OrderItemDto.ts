import type BigNumber from "bignumber.js"

export type OrderItemDto = {
    id: number | null,
    itemId: number | null,
    itemCost: BigNumber,
    quantity: number,
    cost: BigNumber
}