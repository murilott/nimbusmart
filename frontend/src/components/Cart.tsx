import React, { useState } from 'react'
import CartItem from './CartItem'
import type { OrderItemDto } from '../types/OrderItemDto'
import BigNumber from 'bignumber.js'
import type { OrderDto } from '../types/OrderDto'
import "../style/cart.css"
import CheckoutPopup from './CheckoutPopup'
import { toBrl } from '../helper/toPrice'

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
    const [ isCheckoutOpen, setIsCheckoutOpen ] = useState<boolean>(false);
    const [order, setOrder] = useState<OrderDto>(orderObj);

    function toCheckout(open: boolean) {
        setIsCheckoutOpen(open);
    }

    return (
        <div className='content'>
            <h3>Cart: {carts.length} item(s)</h3>

            <hr />

            <div className='card'>
                <div className='cart-item-list'>
                    {carts?.map((orderItem) => (
                        // <li
                        //     onClick={() => selectInventory(inv)}
                        //     className={`${selectedInventory?.id == inv.id
                        //         ? 'inventory-items-category-selected'
                        //         : ''
                        //         }`}
                        // >{inv.name}</li>
                        <CartItem orderItem={orderItem} />
                    ))}
                </div>

                <div>
                    <hr />

                    <div className='cart-checkout-actions'>
                        <button onClick={() => toCheckout(true)}>Checkout</button>

                        <p>Total: {toBrl(order.totalCost)}</p>
                    </div>
                </div>
            </div>

            {isCheckoutOpen && 
                <CheckoutPopup order={order} setOrder={setOrder} toggle={(open) => toCheckout(open)} />
            }
        </div>
    )
}

export default Cart