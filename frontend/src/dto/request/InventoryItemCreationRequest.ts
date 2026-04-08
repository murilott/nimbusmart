import type BigNumber from "bignumber.js";

export type InventoryItemCreationRequest = {
    productId: number,
    inventoryId: number,
    quantity: number,
    price: BigNumber,
}