import type { InventoryCreationRequest } from "../dto/request/InventoryCreationRequest";
import type { InventoryItemCreationRequest } from "../dto/request/InventoryItemCreationRequest";
import type { InventoryDto } from "../types/InventoryDto";
import type { InventoryItemDto } from "../types/InventoryItemDto";
import { api } from "./api";

export const listInventories = async (): Promise<InventoryDto[]> => {
  const response = await api.get<InventoryDto[]>("/inventory/api/v1/inventory");

  return response.data;
};

export const createInventory = async (
    inventoryCreationRequest: InventoryCreationRequest
): Promise<InventoryDto> => {
    const response = await api.post<InventoryDto>(
        `/inventory/api/v1/inventory`,
        inventoryCreationRequest
    );

    return response.data;
};

export const listInventoryItems = async (): Promise<InventoryItemDto[]> => {
  const response = await api.get<InventoryItemDto[]>("/inventory/api/v1/inventory-item");

  return response.data;
};

export const createInventoryItem = async (
    inventoryItemCreationRequest: InventoryItemCreationRequest
): Promise<InventoryItemDto> => {
    const response = await api.post<InventoryItemDto>(
        `/inventory/api/v1/inventory-item`,
        inventoryItemCreationRequest
    );

    return response.data;
};