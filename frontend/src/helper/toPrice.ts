import type BigNumber from "bignumber.js"

export function toBrl(price: BigNumber) {
    return `R$ ${price?.toNumber() ?? 0}`;
}

export function toUsd(price: BigNumber) {
    return `$ ${price?.toNumber() ?? 0}`;
}