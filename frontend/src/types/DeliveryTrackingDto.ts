import type { ShipmentDto } from "./ShipmentDto"

export type DeliveryTrackingDto = {
    id: number | null,
    itemsToDeliver: ShipmentDto[],
    itemsDelivering: ShipmentDto[],
    itemsDelivered: ShipmentDto[],
    itemsCanceled: ShipmentDto[],
}
