import type { ProductCreationRequest } from "../dto/request/ProductCreationRequest";
import type { ProductDto } from "../types/ProductDto";
import { api } from "./api";

type Page<T> = {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
};

export const listProducts = async (): Promise<ProductDto[]> => {
  const response = await api.get<Page<ProductDto>>("/product/products");

  return response.data.content;
};

export const createProduct = async (
    productCreationRequest: ProductCreationRequest
): Promise<ProductDto> => {
    const response = await api.post<ProductDto>(
        `/product/products`,
        productCreationRequest
    );

    return response.data;
};