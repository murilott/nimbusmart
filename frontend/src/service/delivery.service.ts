import type { CancelShipmentRequest } from "../new/request/CancelShipmentRequest";
import type { NextDeliveredRequest } from "../new/request/NextDeliveredRequest";
import type { NextDeliverRequest } from "../new/request/NextDeliverRequest";
import type { DeliveryTrackingDto } from "../types/DeliveryTrackingDto";
import type { ShipmentDto } from "../types/ShipmentDto";
import { api } from "./api";

export const listDeliveryTracking = async (): Promise<DeliveryTrackingDto[]> => {
  const response = await api.get<DeliveryTrackingDto[]>("/api/v1/delivery-tracking");

  return response.data;
};

export const nextDeliver = async (
    nextDeliverRequest: NextDeliverRequest
): Promise<ShipmentDto> => {
    const response = await api.post<ShipmentDto>(
        `/api/v1/delivery-tracking/next-deliver`,
        nextDeliverRequest
    );

    return response.data;
};

export const nextDelivered = async (
    nextDeliveredRequest: NextDeliveredRequest
): Promise<ShipmentDto> => {
    const response = await api.post<ShipmentDto>(
        `/api/v1/delivery-tracking/next-delivered`,
        nextDeliveredRequest
    );

    return response.data;
};

export const cancelShipment = async (
    cancelShipmentRequest: CancelShipmentRequest
): Promise<ShipmentDto> => {
    const response = await api.post<ShipmentDto>(
        `/api/v1/delivery-tracking/cancel-shipment`,
        cancelShipmentRequest
    );

    return response.data;
};