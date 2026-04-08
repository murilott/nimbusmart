import type { OrderItemCreationRequest } from "../dto/request/OrderItemCreationRequest";
import type { OrderDto } from "../types/OrderDto";
import type { OrderItemDto } from "../types/OrderItemDto";
import { api } from "./api";

export const listOrders = async (): Promise<OrderDto[]> => {
  const response = await api.get<OrderDto[]>("/order/api/v1/order");

  return response.data;
};

export const addToOrder = async (
    orderItemCreationRequest: OrderItemCreationRequest
): Promise<OrderItemDto> => {
    const response = await api.post<OrderItemDto>(
        `/order/api/v1/order-item`,
        orderItemCreationRequest
    );

    return response.data;
};