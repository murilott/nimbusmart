import React, { useState } from 'react'
import CartItem from './CartItem'
import type { OrderItemDto } from '../types/OrderItemDto'
import BigNumber from 'bignumber.js'
import type { OrderDto } from '../types/OrderDto'
import "../style/cart.css"
import CheckoutPopup from './CheckoutPopup'
import { toBrl } from '../helper/toPrice'
import { activeOrder, listOrders } from '../service/order.service'
import { useQuery } from '@tanstack/react-query'
import { listInventoryItems } from '../service/inventory.service'
import { listProducts } from '../service/product.service'
import type { InventoryItemDto } from '../types/InventoryItemDto'
import type { ProductDto } from '../types/ProductDto'
import { listDeliveryTracking } from '../service/delivery.service'
import type { DeliveryTrackingDto } from '../types/DeliveryTrackingDto'
import { obtainOrder, obtainProductFromInventoryItem } from '../helper/obtainObjects'
import { formatDate } from '../helper/formatDate'

const carts: OrderItemDto[] = [
    {
        id: 1,
        itemId: 1,
        itemCost: new BigNumber(50),
        quantity: 2,
        cost: new BigNumber(100),
    },
    {
        id: 2,
        itemId: 2,
        itemCost: new BigNumber(20),
        quantity: 1,
        cost: new BigNumber(20),
    },
    {
        id: 3,
        itemId: 3,
        itemCost: new BigNumber(60),
        quantity: 2,
        cost: new BigNumber(120),
    },
    {
        id: 3,
        itemId: 3,
        itemCost: new BigNumber(60),
        quantity: 2,
        cost: new BigNumber(120),
    },
    {
        id: 3,
        itemId: 3,
        itemCost: new BigNumber(60),
        quantity: 2,
        cost: new BigNumber(120),
    },
    {
        id: 3,
        itemId: 3,
        itemCost: new BigNumber(60),
        quantity: 2,
        cost: new BigNumber(120),
    },
]

const orderObj: OrderDto = {
    id: 1,
    items: [],
    status: 'PENDING',
    totalCost: new BigNumber(150),
}

function Cart() {
    const [isCheckoutOpen, setIsCheckoutOpen] = useState<boolean>(false);
    // const [order, setOrder] = useState<OrderDto>(orderObj);

    const { isLoading, error, data: order } = useQuery<OrderDto>({
        queryKey: ['order'],
        queryFn: activeOrder,
    });

    const { data: orders } = useQuery<OrderDto[]>({
        queryKey: ['orders'],
        queryFn: listOrders,
    });

    const { data: inventoryItems } = useQuery<InventoryItemDto[]>({
        queryKey: ['inventoryItems'],
        queryFn: listInventoryItems,
    });

    const { data: products } = useQuery<ProductDto[]>({
        queryKey: ['products'],
        queryFn: listProducts,
    });

    const { data: deliveryTracking } = useQuery<DeliveryTrackingDto[]>({
        queryKey: ['deliveryTracking'],
        queryFn: listDeliveryTracking,
    });

    function toCheckout(open: boolean) {
        setIsCheckoutOpen(open);
    }

    return (
        <div className='content'>
            <h3>Cart: {order?.items?.length ?? 0} item(s)</h3>

            <div className='card'>
                <div className='cart-item-list'>
                    {order?.items?.map((orderItem) => (
                        <CartItem orderItem={orderItem} products={products ?? []} inventoryItems={inventoryItems ?? []} />
                    ))}
                </div>
                <div>
                    <div className='cart-checkout-actions'>
                        <button onClick={() => toCheckout(true)} disabled={!order}>Checkout</button>

                        <p>Total: {toBrl(new BigNumber(order?.totalCost ?? 0))}</p>
                    </div>
                </div>
            </div>

            <h3>Deliveries</h3>

            <div className='card'>
                <div className='cart-item-list'>
                    {deliveryTracking?.length == 0 &&
                        <p>No delivery items.</p>
                    }
                    {deliveryTracking?.length && [
                        ...deliveryTracking[0].itemsToDeliver, //.filter(s => s.status.value == "PENDING")
                        ...deliveryTracking[0].itemsDelivering,
                        ...deliveryTracking[0].itemsDelivered,
                        ...deliveryTracking[0].itemsCanceled,
                    ]
                        .map((ship) => (
                            <div
                                key={ship.id}
                                className='cart-delivery-item'
                            >
                                <div>
                                    {ship.status.value == "PENDING" &&
                                        <>
                                            <p>Status: {ship.status.value}</p>
                                            <p>Requested at: {formatDate(new Date(ship.createdAt ?? ""))}</p>
                                        </>
                                    }
                                    {ship.status.value == "IN_TRANSIT" &&
                                        <>
                                            <p>Status: {ship.status.value}</p>
                                            <p>Dispatched at: {formatDate(new Date(ship.dispatchedAt ?? ""))}</p>
                                        </>
                                    }
                                    {ship.status.value == "DELIVERED" &&
                                        <>
                                            <p>Status: {ship.status.value}</p>
                                            <p>Delivered at: {formatDate(new Date(ship.deliveredAt ?? ""))}</p>
                                        </>
                                    }
                                    {ship.status.value == "FAILED" &&
                                        <>
                                            <p>Status: {ship.status.value}</p>
                                            <p>Canceled at: {formatDate(new Date(ship.failedAt ?? ""))}</p>
                                        </>
                                    }
                                </div>

                                <div className='cart-delivery-content'><p>Content:</p></div>


                                <div>
                                    {obtainOrder(ship?.orderId, orders)?.items.map((item) => (
                                        <p>
                                            {obtainProductFromInventoryItem(
                                                item?.inventoryItem?.itemId,
                                                products,
                                                inventoryItems
                                            )?.name}
                                            ({item.quantity} unit{item.quantity > 1 ? "s" : ""})
                                        </p>
                                    ))}
                                </div>
                            </div>
                        ))
                    }

                </div>
            </div>

            {isCheckoutOpen && order &&
                <CheckoutPopup dataOrder={order} toggle={(open) => toCheckout(open)} />
            }
        </div>
    )
}

export default Cart