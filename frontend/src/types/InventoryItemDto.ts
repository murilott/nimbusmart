import type BigNumber from "bignumber.js";

export type InventoryItemDto = {
    id: number | null,
    productId: number | null,
    quantity: number,
    price: BigNumber,
}