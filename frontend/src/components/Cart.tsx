import React from 'react'
import CartItem from './CartItem'
import type { OrderItemDto } from '../types/OrderItemDto'
import BigNumber from 'bignumber.js'

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
]

function Cart() {
    return (
        <div className='content'>
            <h3>Cart: {carts.length} item(s)</h3>

            <hr />

            <div className='card'>
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
        </div>
    )
}

export default Cart