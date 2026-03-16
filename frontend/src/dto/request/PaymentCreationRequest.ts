import type BigNumber from "bignumber.js"
import type { PaymentMethod } from "../../enums/PaymentMethod"

export type PaymentCreationRequest = {
    name: string,
    funds: BigNumber,
    creditLimit: BigNumber,
    method: PaymentMethod
}