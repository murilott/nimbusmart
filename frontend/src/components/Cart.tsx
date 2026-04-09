import React, { useState } from 'react'
import CartItem from './CartItem'
import type { OrderItemDto } from '../types/OrderItemDto'
import BigNumber from 'bignumber.js'
import type { OrderDto } from '../types/OrderDto'
import "../style/cart.css"
import CheckoutPopup from './CheckoutPopup'
import { toBrl } from '../helper/toPrice'
import { activeOrder, listOrders } from '../service/order.service'
import { useQuery } from '@tanstack/react-query'
import { listInventoryItems } from '../service/inventory.service'
import { listProducts } from '../service/product.service'
import type { InventoryItemDto } from '../types/InventoryItemDto'
import type { ProductDto } from '../types/ProductDto'

const carts: OrderItemDto[] = [
    {
        id: 1,
        itemId: 1,
        itemCost: new BigNumber(50),
        quantity: 2,
        cost: new BigNumber(100),
    },
    {
        id: 2,
        itemId: 2,
        itemCost: new BigNumber(20),
        quantity: 1,
        cost: new BigNumber(20),
    },
    {
        id: 3,
        itemId: 3,
        itemCost: new BigNumber(60),
        quantity: 2,
        cost: new BigNumber(120),
    },
    {
        id: 3,
        itemId: 3,
        itemCost: new BigNumber(60),
        quantity: 2,
        cost: new BigNumber(120),
    },
    {
        id: 3,
        itemId: 3,
        itemCost: new BigNumber(60),
        quantity: 2,
        cost: new BigNumber(120),
    },
    {
        id: 3,
        itemId: 3,
        itemCost: new BigNumber(60),
        quantity: 2,
        cost: new BigNumber(120),
    },
]

const orderObj: OrderDto = {
    id: 1,
    items: [],
    status: 'PENDING',
    totalCost: new BigNumber(150),
}

function Cart() {
    const [isCheckoutOpen, setIsCheckoutOpen] = useState<boolean>(false);
    // const [order, setOrder] = useState<OrderDto>(orderObj);

    const { isLoading, error, data: order } = useQuery<OrderDto>({
        queryKey: ['order'],
        queryFn: activeOrder,
    });

    const { data: inventoryItems } = useQuery<InventoryItemDto[]>({
        queryKey: ['inventoryItems'],
        queryFn: listInventoryItems,
    });

    const { data: products } = useQuery<ProductDto[]>({
        queryKey: ['products'],
        queryFn: listProducts,
    });

    function toCheckout(open: boolean) {
        setIsCheckoutOpen(open);
    }

    return (
        <div className='content'>
            <h3>Cart: {order?.items?.length ?? 0} item(s)</h3>

            <div className='card'>
                <div className='cart-item-list'>
                    {order?.items?.map((orderItem) => (
                        <CartItem orderItem={orderItem} products={products ?? []} inventoryItems={inventoryItems ?? []}/>
                    ))}
                </div>

                <div>
                    <hr />

                    <div className='cart-checkout-actions'>
                        <button onClick={() => toCheckout(true)} disabled={!order}>Checkout</button>

                        <p>Total: {toBrl(new BigNumber(order?.totalCost ?? 0))}</p>
                    </div>
                </div>
            </div>

            {isCheckoutOpen && order &&
                <CheckoutPopup dataOrder={order} toggle={(open) => toCheckout(open)} />
            }
        </div>
    )
}

export default Cart