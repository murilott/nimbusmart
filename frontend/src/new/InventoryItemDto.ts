import BigNumber from "bignumber.js";
import type { InventoryItemDto } from "../types/InventoryItemDto";
import type { ProductDto } from "../types/ProductDto";

export const inventoryItemNew: InventoryItemDto = {
    id: 0,
    productId: 0,
    quantity: 0,
    price: new BigNumber(0),
}