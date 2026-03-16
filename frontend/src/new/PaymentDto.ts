import BigNumber from "bignumber.js";
import type { PaymentDto } from "../types/PaymentDto";

export const paymentNew: PaymentDto = {
    id: null,
    name: "",
    funds: new BigNumber(0),
    creditLimit: new BigNumber(0),
    limitSpent: new BigNumber(0),
    method: "PIX"
}