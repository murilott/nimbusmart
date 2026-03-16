import type BigNumber from "bignumber.js"
import type { PaymentMethod } from "../enums/PaymentMethod"

export type PaymentDto = {
    id: number | null,
    name: string,
    funds: BigNumber,
    creditLimit: BigNumber,
    limitSpent: BigNumber,
    method: PaymentMethod,
}