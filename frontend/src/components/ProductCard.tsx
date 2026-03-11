import React from 'react'
import "../style/productcard.css"
import { Link, useNavigate } from 'react-router-dom'
import type { ProductDto } from '../types/ProductDto';

interface ProductCardProps {
    product: ProductDto
}

function ProductCard({ product }: ProductCardProps) {
    const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/product/${product?.id}`);
  };

  return (
    <div className='product-card' onClick={handleClick}>
        <img className='product-card-img' src="https://placehold.co/150x180" alt="Product image" width="150" height="180" />
        <h4 className='product-card-title'>{product?.name}</h4>
        <div className='product-card-desc'>
            <span className='product-card-description'>{product?.description}</span>
            <span className='product-card-price'>Price</span>
        </div>
    </div>
  )
}

export default ProductCard