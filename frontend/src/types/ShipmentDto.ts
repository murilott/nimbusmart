import type { ShipmentStatus } from "../enums/ShipmentStatus";

export type ShipmentDto = {
    id: number | null,
    orderId: number | null,
    status: ShipmentStatus,
    destinyAddress: string,
    createdAt: Date,
    dispatchedAt: Date | null,
    deliveredAt: Date | null,
    failedAt: Date | null,

    duration: number,
}