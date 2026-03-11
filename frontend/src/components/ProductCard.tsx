import React from 'react'
import "../style/productcard.css"
import { Link, useNavigate } from 'react-router-dom'

interface ProductCardProps {
    name: string,
    description: string,
    price: string,
}

function ProductCard({ name, description, price }: ProductCardProps) {
    const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/product/${name}`);
  };
  
  return (
    <div className='product-card' onClick={handleClick}>
        <img className='product-card-img' src="https://placehold.co/150x180" alt="Product image" width="150" height="180" />
        <h4 className='product-card-title'>{name}</h4>
        <div className='product-card-desc'>
            <span className='product-card-description'>{description}</span>
            <span className='product-card-price'>{price}</span>
        </div>
    </div>
  )
}

export default ProductCard