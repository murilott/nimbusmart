import React, { useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import type { ProductDto } from '../types/ProductDto';
import { productDtoNew } from '../new/ProductDto';
import { useQuery } from '@tanstack/react-query';
import BigNumber from 'bignumber.js';
import type { InventoryItemDto } from '../types/InventoryItemDto';
import { inventoryItemNew } from '../new/InventoryItemDto';
import "../style/productpage.css"
import { toBrl } from '../helper/toPrice';

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
        description: "Teclado Logitech aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa bbbbbbbbbbbc cccccccccccccccc ddddddddddddd",
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

function ProductPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    // const [product, setProduct] = useState<ProductDto>(productDtoNew);

    const [inventoryItem, setInventoryItem] = useState<InventoryItemDto>(() => {
        const item: InventoryItemDto = items.find(p => id == p.productId?.toString()) ?? inventoryItemNew;

        return {
            id: item.id,
            productId: item.productId,
            price: item.price,
            quantity: item.quantity,
        }
    });

    const [product, setProduct] = useState<ProductDto>(() => {
        const prod: ProductDto = prods.find(p => id == p.id?.toString()) ?? productDtoNew;

        return {
            id: prod.id,
            name: prod.name,
            description: prod.description,
            image: prod.image,
            tags: prod.tags
        }
    });

    // const { inventoryItem, isLoading, isError, error } = useQuery({
    //     queryKey: ['inventoryItem', id], // 2. O ID na Key força o refetch se a URL mudar
    //     // queryFn: () => fetchProduct(id), // 3. Passa o ID para a função de fetch
    //     enabled: !!id, // 4. Só executa se o ID existir (boa prática)
    // });

    // if (isLoading) return <div>Carregando...</div>;
    // if (isError) return <div>Erro: {error.message}</div>;

    return (
        <div className='content'>
            <div className='product-page-body'>
                <div className='product-page-info'>
                    <div className='product-page-return'>
                        <button onClick={() => {
                            navigate(`../`);
                        }}>← Return</button>
                    </div>

                    <div>
                        <h2>{product?.name}</h2>
                        <hr />
                    </div>

                    <div className='product-page-img'>
                        <img className='product-card-img' src="https://placehold.co/400x200" alt="Product image" width="400" height="200" />
                    </div>


                    <div className='product-page-desc card'>
                        <h3>Description</h3>

                        <p>{product.description}</p>
                    </div>
                </div>

                <div className='product-page-checkout card'>
                    <h3 className='product-page-addtocart'>Add to Cart</h3>
                    <p><strong>Price: {toBrl(inventoryItem?.price)}</strong></p>
                    <p>Available: {inventoryItem?.quantity}</p>
                    <div>
                        <label htmlFor="quantity">Quantity: </label>
                        <input type="number" />
                    </div>
                    <button>Add to cart</button>
                </div>

            </div>
        </div>
    )
}

export default ProductPage