import BigNumber from "bignumber.js";
import type { InventoryItemCreationRequest } from "../../dto/request/InventoryItemCreationRequest";

export const inventoryItemCreationNew: InventoryItemCreationRequest = {
    productId: 0,
    inventoryId: 0,
    quantity: 1,
    price: new BigNumber(0),
}