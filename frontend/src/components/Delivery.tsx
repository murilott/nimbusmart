import React, { useEffect, useState } from 'react'
import "../style/delivery.css"
import type { ShipmentDto } from '../types/ShipmentDto'
import { shipmentNew } from '../new/ShipmentDto'
import { formatDate } from '../helper/formatDate';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import type { DeliveryTrackingDto } from '../types/DeliveryTrackingDto';
import { listDeliveryTracking, nextDeliver, nextDelivered } from '../service/delivery.service';
import type { OrderDto } from '../types/OrderDto';
import { activeOrder, listOrders } from '../service/order.service';
import { listInventoryItems } from '../service/inventory.service';
import type { InventoryItemDto } from '../types/InventoryItemDto';
import { obtainInventoryItem, obtainProductFromInventoryItem } from '../helper/obtainObjects';
import { listProducts } from '../service/product.service';
import type { ProductDto } from '../types/ProductDto';

const ships: ShipmentDto[] = [
    {
        id: 1,
        orderId: 1,
        status: "PENDING",
        destinyAddress: "Sample Address",
        createdAt: new Date("2026-03-15T10:00:00"),
        dispatchedAt: null,
        deliveredAt: null,
        failedAt: null,
        duration: 0,
    },
    {
        id: 2,
        orderId: 2,
        status: "IN_TRANSIT",
        destinyAddress: "Sample Address",
        createdAt: new Date("2026-03-15T10:00:00"),
        dispatchedAt: new Date("2026-03-16T08:00:00"),
        deliveredAt: null,
        failedAt: null,
        duration: 45,
    },
    {
        id: 3,
        orderId: 3,
        status: "DELIVERED",
        destinyAddress: "Sample Address",
        createdAt: new Date("2026-03-15T10:00:00"),
        dispatchedAt: new Date("2026-03-16T08:00:00"),
        deliveredAt: new Date("2026-03-17T14:30:00"),
        failedAt: null,
        duration: 45,
    },
    {
        id: 4,
        orderId: 4,
        status: "FAILED",
        destinyAddress: "Sample Address",
        createdAt: new Date("2026-03-15T10:00:00"),
        dispatchedAt: new Date("2026-03-16T08:00:00"),
        deliveredAt: new Date("2026-03-17T14:30:00"),
        failedAt: new Date("2026-03-18T14:30:00"),
        duration: 45,
    },
];

function Delivery() {
    const [selectedShipment, setSelectedShipment] = useState<ShipmentDto | null>();
    const [selectedShipmentOrder, setSelectedShipmentOrder] = useState<OrderDto | null>();

    const queryClient = useQueryClient();

    const { mutateAsync: mNextDeliver, isPending } = useMutation({
        mutationFn: nextDeliver,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['deliveryTracking'] });
        },
    });

    const { mutateAsync: mNextDelivering, isPending: isDeliveredPending } = useMutation({
        mutationFn: nextDelivered,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['deliveryTracking'] });
        },
    });

    const { data: deliveryTracking } = useQuery<DeliveryTrackingDto[]>({
        queryKey: ['deliveryTracking'],
        queryFn: listDeliveryTracking,
    });

    const { data: inventoryItems } = useQuery<InventoryItemDto[]>({
        queryKey: ['inventoryItems'],
        queryFn: listInventoryItems,
    });

    function selectShipment(shipment: ShipmentDto) {
        setSelectedShipment(shipment);
    }

    const { data: orders } = useQuery<OrderDto[]>({
        queryKey: ['orders'],
        queryFn: listOrders,
    });

    const { data: products } = useQuery<ProductDto[]>({
        queryKey: ['products'],
        queryFn: listProducts,
    });

    useEffect(() => {
        const ord = orders?.find((o) => selectedShipment?.orderId == o.id)
        setSelectedShipmentOrder(ord);
    }, [selectedShipment?.orderId])

    function debug() {
        console.log(deliveryTracking);
    }

    return (
        <div className='content'>
            <h3>Delivery</h3>

            <hr />
            
            <button onClick={debug}>Debug</button>

            <p>Manage delivery shipments.</p>

            <div className='delivery-column-wrapper'>
                <div className='card card-column delivery-column'>
                    <h4>Pending deliveries</h4>

                    <div className='delivery-column-shipment-list'>
                        {deliveryTracking?.length && deliveryTracking[0].itemsToDeliver //.filter(s => s.status.value == "PENDING")
                            .map((ship) => (
                                <div
                                    key={ship.id}
                                    onClick={() => selectShipment(ship)}
                                    className={`${ship.id == selectedShipment?.id ? 'shipment-selected' : ''}`}
                                >Shipment {deliveryTracking[0].itemsToDeliver.indexOf(ship)}</div>
                            ))
                        }
                    </div>

                    <button>Get next pending shipment</button>
                </div>

                <div className='card card-column delivery-column'>
                    <h4>In transit deliveries</h4>

                    <div className='delivery-column-shipment-list'>
                        {deliveryTracking?.length && deliveryTracking[0].itemsDelivering.filter(s => s.status.value == "IN_TRANSIT")
                            .map((ship) => (
                                <div
                                    key={ship.id}
                                    onClick={() => selectShipment(ship)}
                                    className={`${ship.id == selectedShipment?.id ? 'shipment-selected' : ''}`}
                                >Shipment {ship.id}</div>
                            ))
                        }
                    </div>

                    <button>Deliver next shipment</button>
                </div>

                <div className='card card-column delivery-column'>
                    <h4>Finished deliveries</h4>

                    <div className='delivery-column-shipment-list'>
                        {deliveryTracking?.length && deliveryTracking[0].itemsDelivered.filter(s => (s.status.value == "DELIVERED") || (s.status.value == "FAILED"))
                            .map((ship) => (
                                <div
                                    key={ship.id}
                                    onClick={() => selectShipment(ship)}
                                    className={`${ship.id == selectedShipment?.id ? 'shipment-selected' : ''}`}
                                >Shipment {ship.id}</div>
                            ))
                        }
                    </div>
                </div>
            </div>

            <div className='card card-footer shipment-info'>
                {selectedShipment ?
                    <h4>Shipment {selectedShipment.id} - {selectedShipmentOrder?.items.length} item(s)</h4>
                    :
                    <h4>Select a shipment</h4>
                }

                {selectedShipment &&
                    <div className='shipment-info-details'>
                        <div>
                            <p>Status: {selectedShipment.status.value}</p>

                            <p>Created at: {formatDate(new Date(selectedShipment.createdAt))}</p>
                            <p>Dispatched at: {formatDate(new Date(selectedShipment?.dispatchedAt ?? ""))}</p>
                            <p>Delivered at: {formatDate(new Date(selectedShipment?.deliveredAt ?? ""))}</p>

                            {selectedShipment.status.value == "FAILED" &&
                                <p>Canceled at: {formatDate(new Date(selectedShipment.failedAt ?? ""))}</p>
                            }
                        </div>
                    </div>
                }
            </div>
        </div>
    )
}

export default Delivery