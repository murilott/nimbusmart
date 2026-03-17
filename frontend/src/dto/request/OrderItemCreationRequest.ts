export type OrderItemCreationRequest = {
    orderId: number | null
    inventoryItemId: number | null,
    quantity: number,
}