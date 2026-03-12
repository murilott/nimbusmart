import React, { useEffect, useState } from 'react'
import "../style/productcard.css"
import { Link, useNavigate } from 'react-router-dom'
import type { ProductDto } from '../types/ProductDto';
import type { InventoryItemDto } from '../types/InventoryItemDto';
import { productNew } from '../new/ProductDto';
import { toBrl } from '../helper/toPrice';

interface ProductCardProps {
    inventoryItem: InventoryItemDto
}

const prods: ProductDto[] = [
    {
        id: 1,
        name: "Mouse",
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

function ProductCard({ inventoryItem }: ProductCardProps) {
    const navigate = useNavigate();
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

    const handleClick = () => {
        navigate(`/product/${product?.id}`);
    };

    return (
        <div className='product-card' onClick={handleClick}>
            <img className='product-card-img' src="https://placehold.co/150x180" alt="Product image" width="150" height="180" />
            <h4 className='product-card-title'>{product?.name}</h4>
            <div className='product-card-desc'>
                <span className='product-card-description'>{product?.description}</span>
                <span className='product-card-price'>{toBrl(inventoryItem?.price)}</span>
            </div>
        </div>
    )
}

export default ProductCard