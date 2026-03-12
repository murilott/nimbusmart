import type { InventoryItemDto } from "./InventoryItemDto";

export type InventoryDto = {
    id: number | null,
    items: InventoryItemDto[],
    createdAt: Date,
    location: string,
    name: string,
}