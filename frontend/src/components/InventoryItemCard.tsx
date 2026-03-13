import React from 'react'
import type { InventoryItemDto } from '../types/InventoryItemDto'
import "../style/inventoryitemcard.css"

interface InventoryItemCardProps {
    item: InventoryItemDto,
    onClick: (item: InventoryItemDto) => void,
    // selectedItem?: InventoryItemDto | null,
    onSelected?: boolean,
}

function InventoryItemCard({ item, onClick, onSelected }: InventoryItemCardProps) {
  return (
    <div 
        className={`inventory-item-card-div ${onSelected
            ? 'inventory-item-card-selected'
            : ''
        }`} 
        onClick={() => onClick(item)
    }>
        <p>Item Id: {item.id}</p>
        <p>Product Id: {item.productId}</p>
        <p>Quantity: {item.quantity}</p>
        <p>Price: {item.price.toNumber()}</p>
    </div>
  )
}

export default InventoryItemCard