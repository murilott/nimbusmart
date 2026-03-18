import React from 'react'
import "../style/home.css"
import ProductCard from './ProductCard'
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import type { InventoryItemDto } from '../types/InventoryItemDto';
import type { ProductDto } from '../types/ProductDto';
import BigNumber from 'bignumber.js';
import { listInventoryItems } from '../service/inventory.service';

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

function Home() {
    // const { inventoryItemList, isLoading, isError, error } = useQuery({
    //     queryKey: ['inventoryItem'],
    //     // queryFn: () => fetchProduct(id), // 3. Passa o ID para a função de fetch
    // });

    const queryClient = useQueryClient();

    const { isLoading, error, data: inventoryItems } = useQuery<InventoryItemDto[]>({
            queryKey: ['inventoryItems'],
            queryFn: listInventoryItems,
        });

    return (
        <div className='content'>
            <h3>Products</h3>
            <hr />

            <div>
                <label htmlFor="">Search:</label>
                <input type="text" />
            </div>

            <div>
                <div className='product-list'>
                    {/* {(inventoryItemList ?? []).length == 0 && !isLoading && !error &&
                        <p>No inventoryItemList found.</p>
                    } */}

                    {items?.map((item) => (
                        <ProductCard inventoryItem={item} />
                    ))}
                    {/* <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                    <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                    <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                    <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                    <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                    <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                    <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                    <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                    <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                    <ProductCard name='Product' description='Description...' price='R$ 49,99' /> */}
                </div>
            </div>
        </div>
    )
}

export default Home