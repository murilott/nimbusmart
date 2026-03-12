import type BigNumber from "bignumber.js"

export function toPrice(price: BigNumber) {
    return `R$ ${price?.toNumber() ?? 0}`;
}