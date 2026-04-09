import type BigNumber from "bignumber.js"
import type { ItemSnapshot } from "./ItemSnapshot"

export type OrderItemDto = {
    id: number | null,
    inventoryItem: ItemSnapshot,
    quantity: number,
    cost: BigNumber
}