import React, { useEffect, useState } from 'react'
import "../style/productcard.css"
import { Link, useNavigate } from 'react-router-dom'
import type { ProductDto } from '../types/ProductDto';
import type { InventoryItemDto } from '../types/InventoryItemDto';
import { productNew } from '../new/ProductDto';
import { toBrl } from '../helper/toPrice';
import BigNumber from 'bignumber.js';

interface ProductCardProps {
    inventoryItem: InventoryItemDto,
    products?: ProductDto[],
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

function ProductCard({ inventoryItem, products }: ProductCardProps) {
    const navigate = useNavigate();
    const [product, setProduct] = useState<ProductDto>(() => {
        const prod: ProductDto = products?.find(p => inventoryItem.productId == p.id) ?? productNew;

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
            <img className='product-card-img' src={product?.image} alt="Product image" width="auto" height="180" />
            <h4 className='product-card-title'>{product?.name}</h4>
            <div className='product-card-desc'>
                <span className='product-card-description'>{product?.description}</span>
                <span className='product-card-price'>{toBrl(new BigNumber(inventoryItem?.price))}</span>
            </div>
        </div>
    )
}

export default ProductCard