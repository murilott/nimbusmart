import type { InventoryItemDto } from "../types/InventoryItemDto";
import type { OrderDto } from "../types/OrderDto";
import type { ProductDto } from "../types/ProductDto"

export const obtainProduct = (productId: number | null | undefined, productList: ProductDto[] | undefined): ProductDto | null => {
    if (!productId) return null;

    return productList?.find((i) => productId == i.id) ?? null;
}

export const obtainOrder = (orderId: number | null | undefined, orderList: OrderDto[] | undefined): OrderDto | null => {
    if (!orderId) return null;

    return orderList?.find((i) => orderId == i.id) ?? null;
}

export const obtainInventoryItem = (inventoryItemId: number | null | undefined, inventoryItemList: InventoryItemDto[] | undefined): InventoryItemDto | null => {
    if (!inventoryItemId) return null;

    return inventoryItemList?.find((i) => inventoryItemId == i.id) ?? null;
}

export const obtainProductFromInventoryItem = (
        inventoryItemId: number | null,
        productList: ProductDto[] | undefined,
        inventoryItemList: InventoryItemDto[] | undefined
    ): ProductDto | null | undefined => {
        return obtainProduct(
            obtainInventoryItem(inventoryItemId, inventoryItemList)?.productId,
            productList
        );
    }