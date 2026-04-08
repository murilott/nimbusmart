import React from 'react'
import type { InventoryItemDto } from '../types/InventoryItemDto'
import "../style/inventoryitemcard.css"
import BigNumber from 'bignumber.js'
import type { ProductDto } from '../types/ProductDto'

interface InventoryItemCardProps {
    item: InventoryItemDto,
    onClick: (item: InventoryItemDto) => void,
    // selectedItem?: InventoryItemDto | null,
    onSelected?: boolean,
    products?: ProductDto[],
}

function InventoryItemCard({ item, onClick, onSelected, products }: InventoryItemCardProps) {
  return (
    <div 
        className={`inventory-item-card-div ${onSelected
            ? 'inventory-item-card-selected'
            : ''
        }`} 
        onClick={() => onClick(item)
    }>
        <p className='inventory-item-card-item-id'>Item Id: {item.id}</p>
        <p>Product: {products?.find(p => item.productId == p.id)?.name}</p>
        <p>Quantity: {item.quantity}</p>
        <p>Price: {new BigNumber(item.price).toNumber()}</p>
    </div>
  )
}

export default InventoryItemCard