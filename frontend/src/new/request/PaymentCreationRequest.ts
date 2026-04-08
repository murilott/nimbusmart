import BigNumber from "bignumber.js";
import type { PaymentCreationRequest } from "../../dto/request/PaymentCreationRequest";

export const paymentCreationNew: PaymentCreationRequest = {
    name: "",
    funds: new BigNumber(0),
    creditLimit: new BigNumber(0),
    method: { value: "PIX" },
}