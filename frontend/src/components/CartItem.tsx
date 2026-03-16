import React, { useState } from 'react'
import type { OrderItemDto } from '../types/OrderItemDto'
import type { ProductDto } from '../types/ProductDto';
import BigNumber from 'bignumber.js';
import type { InventoryItemDto } from '../types/InventoryItemDto';
import { inventoryItemNew } from '../new/InventoryItemDto';
import { productNew } from '../new/ProductDto';
import "../style/cartitem.css"

interface CartItemProps {
    orderItem: OrderItemDto,
}

const prods: ProductDto[] = [
    {
        id: 1,
        name: "Mouse zzzzzzzzzzzzzzzzzzzzzzz   zzzzzzzz  zzzzzz zzzzzzzzzzz zzzzzzzzzzzzzzz zzzzzzz",
        description: "Mouse Logitech",
        tags: [],
        image: ""
    },
    {
        id: 2,
        name: "Teclado",
        description: "Teclado Logitech aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
        tags: [],
        image: ""
    },
    {
        id: 3,
        name: "Pendrive",
        description: "Pendrive Logitech",
        tags: [],
        image: ""
    },
]

const items: InventoryItemDto[] = [
    {
        id: 1,
        productId: 1,
        quantity: 5,
        price: new BigNumber(49.99),
    },
    {
        id: 2,
        productId: 2,
        quantity: 10,
        price: new BigNumber(99.99),
    },
    {
        id: 3,
        productId: 3,
        quantity: 10,
        price: new BigNumber(49.99),
    },
]

function CartItem({ orderItem }: CartItemProps) {
    const [inventoryItem, setInventoryItem] = useState<InventoryItemDto>(() => {
        const item: InventoryItemDto = items.find(p => p.id == orderItem?.itemId) ?? inventoryItemNew;

        return {
            id: item.id,
            productId: item.productId,
            price: item.price,
            quantity: item.quantity,
        }
    });

    const [product, setProduct] = useState<ProductDto>(() => {
        const prod: ProductDto = prods.find(p => inventoryItem.productId == p.id) ?? productNew;

        return {
            id: inventoryItem.productId,
            name: prod.name,
            description: prod.description,
            image: prod.image,
            tags: prod.tags
        }
    });
    return (
        <div className='cart-item'>
            <div className='cart-item-info'>
                <div className='cart-item-info-img'>
                    <img src={product.image} alt={product.name} sizes='50' />
                </div>

                <div className='cart-item-info-name'>
                    <h4>{product.name}</h4>
                </div>
            </div>

            <div className='cart-item-cost'>
                <div>
                    <p>Unit Cost: {orderItem.itemCost.toString()}</p>
                    <p>Quantity: {orderItem.quantity}</p>
                </div>

                <div>
                    <p>Total: {orderItem.cost.toString()}</p>
                </div>
            </div>
        </div>
    )
}

export default CartItem