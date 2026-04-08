import type { PaymentCreationRequest } from "../dto/request/PaymentCreationRequest";
import type { TransactionCreationRequest } from "../dto/request/TransactionCreationRequest";
import type { PaymentDto } from "../types/PaymentDto";
import type { TransactionDto } from "../types/TransactionDto";
import { api } from "./api";

export const listPayments = async (): Promise<PaymentDto[]> => {
  const response = await api.get<PaymentDto[]>("/payment/api/v1/payment");

  return response.data;
};

export const createPayment = async (
    paymentCreationRequest: PaymentCreationRequest
): Promise<PaymentDto> => {
    const response = await api.post<PaymentDto>(
        `/payment/api/v1/payment`,
        paymentCreationRequest
    );

    return response.data;
};

export const listTransactions = async (): Promise<TransactionDto[]> => {
  const response = await api.get<TransactionDto[]>("/payment/api/v1/transaction");

  return response.data;
};

export const createTransaction = async (
    transactionCreationRequest: TransactionCreationRequest
): Promise<TransactionDto> => {
    const response = await api.post<TransactionDto>(
        `/payment/api/v1/transaction`,
        transactionCreationRequest
    );

    return response.data;
};