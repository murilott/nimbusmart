import type { ProductCreationRequest } from "../dto/request/ProductCreationRequest";
import type { ProductDto } from "../types/ProductDto";
import { api } from "./api";

export const listProducts = async (): Promise<ProductDto[]> => {
  const response = await api.get<ProductDto[]>("/api/v1/product");

  return response.data;
};

export const createProduct = async (
    productCreationRequest: ProductCreationRequest
): Promise<ProductDto> => {
    const response = await api.post<ProductDto>(
        `/api/v1/product`,
        productCreationRequest
    );

    return response.data;
};