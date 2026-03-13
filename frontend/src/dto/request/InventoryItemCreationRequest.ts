import type BigNumber from "bignumber.js";

export type InventoryItemCreationRequest = {
    productId: number,
    quantity: number,
    price: BigNumber,
}