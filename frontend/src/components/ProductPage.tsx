import React, { useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import type { ProductDto } from '../types/ProductDto';
import { productNew } from '../new/ProductDto';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import BigNumber from 'bignumber.js';
import type { InventoryItemDto } from '../types/InventoryItemDto';
import { inventoryItemNew } from '../new/InventoryItemDto';
import "../style/productpage.css"
import { toBrl } from '../helper/toPrice';
import InputForm from './InputForm';
import type { OrderItemCreationRequest } from '../dto/request/OrderItemCreationRequest';
import { OrderItemCreationNew } from '../new/request/OrderItemCreationRequest';
import { listProducts } from '../service/product.service';
import { listInventoryItems } from '../service/inventory.service';
import { addToOrder } from '../service/order.service';

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
    const [orderItem, setOrderItem] = useState<OrderItemCreationRequest>(OrderItemCreationNew);

    const queryClient = useQueryClient();

    const { data: products } = useQuery<ProductDto[]>({
        queryKey: ['products'],
        queryFn: listProducts,
    });

    const { data: inventoryItems } = useQuery<InventoryItemDto[]>({
        queryKey: ['inventoryItems'],
        queryFn: listInventoryItems,
    });

    const [inventoryItem, setInventoryItem] = useState<InventoryItemDto>(() => {
        const item: InventoryItemDto = inventoryItems?.find(p => id == p.productId?.toString()) ?? inventoryItemNew;

        return {
            id: item.id,
            productId: item.productId,
            price: item.price,
            quantity: item.quantity,
        }
    });

    const [product, setProduct] = useState<ProductDto>(() => {
        const prod: ProductDto = products?.find(p => id == p.id?.toString()) ?? productNew;

        return {
            id: prod.id,
            name: prod.name,
            description: prod.description,
            image: prod.image,
            tags: prod.tags
        }
    });

    const handleOrderItem = (e:
        React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement | null>
    ) => {
        const { name, value } = e.target;

        setOrderItem((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    // const { inventoryItem, isLoading, isError, error } = useQuery({
    //     queryKey: ['inventoryItem', id], // 2. O ID na Key força o refetch se a URL mudar
    //     // queryFn: () => fetchProduct(id), // 3. Passa o ID para a função de fetch
    //     enabled: !!id, // 4. Só executa se o ID existir (boa prática)
    // });

    // if (isLoading) return <div>Carregando...</div>;
    // if (isError) return <div>Erro: {error.message}</div>;

    const { mutateAsync, isPending } = useMutation({
        mutationFn: addToOrder,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['orders'] });
        },
    });

    async function addToCart() {
        if (orderItem.quantity > inventoryItem.quantity) {
            alert("Order quantity cannot exceed stock");
            return;
        }

        if (orderItem.quantity <= 0) {
            alert("Order quantity must be greater than 0");
            return;
        }

        const payload: OrderItemCreationRequest = {
            ...orderItem,
            inventoryItemId: inventoryItem.id
        }

        
        try {
            const response = await mutateAsync(payload);
            console.log(response);
            navigate(`/cart`);
            // setProductCreation({ ...productCreationNew });
        } catch (err: unknown) {
            console.error("Unexpected error:", err);
        }
        
    }


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
                        <img className='product-card-img' src={product?.image} alt="Product image" width="auto" height="400px" />
                    </div>


                    <div className='product-page-desc card'>
                        <h3>Description</h3>

                        <p>{product.description}</p>
                    </div>
                </div>

                <div className='product-page-checkout card'>
                    <h3 className='product-page-addtocart'>Add to Cart</h3>
                    <p><strong>Price: {toBrl(new BigNumber(inventoryItem?.price))}</strong></p>
                    <p>Available: {inventoryItem?.quantity}</p>

                    <InputForm
                        field='Quantity:'
                        type='number'
                        name='quantity'
                        value={orderItem.quantity}
                        onChange={handleOrderItem}
                    />

                    <button 
                        onClick={addToCart}
                        disabled={orderItem.quantity <= 0 || orderItem.quantity > inventoryItem.quantity}
                    >Add to cart</button>
                </div>

            </div>
        </div>
    )
}

export default ProductPage