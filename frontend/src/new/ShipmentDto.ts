import type { ShipmentDto } from "../types/ShipmentDto";

export const shipmentNew: ShipmentDto = {
    id: null,
    orderId: null,
    status: "PENDING",
    destinyAddress: "",
    createdAt: new Date(),
    dispatchedAt: new Date(),
    deliveredAt: new Date(),
    failedAt: new Date(),

    duration: 0,
}