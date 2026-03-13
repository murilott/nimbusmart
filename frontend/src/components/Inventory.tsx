import React, { useState, type ChangeEvent } from 'react'
import "../style/inventory.css"
import type { ProductDto } from '../types/ProductDto';
import type { OrderItemDto } from '../types/OrderItemDto';
import { OrderItemNew } from '../new/OrderItemDto';
import type { InventoryItemDto } from '../types/InventoryItemDto';
import { inventoryItemNew } from '../new/InventoryItemDto';
import BigNumber from 'bignumber.js';
import type { InventoryCreationRequest } from '../dto/request/InventoryCreationRequest';
import { inventoryCreationNew } from '../new/request/InventoryCreationRequest';
import InputForm from './InputForm';
import type { InventoryItemCreationRequest } from '../dto/request/InventoryItemCreationRequest';
import { inventoryItemCreationNew } from '../new/request/InventoryItemCreationRequest';
import type { InventoryDto } from '../types/InventoryDto';
import InventoryItemCard from './InventoryItemCard';

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

const invs: InventoryDto[] = [
    { id: 1, items: [], name: "Hardware", location: "Shelf A1", createdAt: new Date },
    { id: 2, items: [...items], name: "Peripherals", location: "Shelf A2", createdAt: new Date },
]

function Inventory() {
    // const [ orderItem, setOrderItem ] = useState<OrderItemDto>(OrderItemNew);
    // const [inventoryItem, setInventoryItem] = useState<InventoryItemDto>(inventoryItemNew);
    const [inventoryCreation, setInventoryCreation] = useState<InventoryCreationRequest>(inventoryCreationNew);
    const [inventoryItemCreation, setInventoryItemCreation] = useState<InventoryItemCreationRequest>(inventoryItemCreationNew);
    
    const [selectedInventory, setSelectedInventory] = useState<InventoryDto | null>(null);
    const [selectedInventoryItem, setSelectedInventoryItem] = useState<InventoryItemDto | null>(null);
    const [editInventoryItem, setEditInventoryItem] = useState<InventoryItemCreationRequest>(inventoryItemCreationNew);

    const [bNewInventory, setBNewInventory] = useState<boolean>(false);
    const [bNewItem, setBNewItem] = useState<boolean>(false);
    const [bEditItem, setBEditItem] = useState<boolean>(false);

    const handleInventoryItem = (e:
        React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement | null>
    ) => {
        const { name, value } = e.target;

        setInventoryItemCreation((prev) => ({
            ...prev,
            [name]: name === "price"
                ? new BigNumber(value ?? 0)
                : value,
        }));
    };

    const handleEditInventoryItem = (e:
        React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement | null>
    ) => {
        const { name, value } = e.target;
        
        setEditInventoryItem((prev) => ({
            ...prev,
            [name]: name === "price"
                ? new BigNumber(value ?? 0)
                : value,
        }));
    };

    const handleInventoryCreation = (e:
        React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement | null>
    ) => {
        const { name, value } = e.target;

        setInventoryCreation({
            ...inventoryCreation,
            [name]: value,
        });
    };

    function changeView(opt: string) {
        if (opt === "a") {
            setBNewItem(true);
            setBEditItem(false);
            setBNewInventory(false);
        }
        if (opt === "b") {
            setBNewItem(false);
            setBEditItem(true);
            setBNewInventory(false);
        }
        if (opt === "c") {
            setBNewItem(false);
            setBEditItem(false);
            setBNewInventory(true);
        }
    }

    function createItem() {
        const payload = { ...inventoryItemCreation };

        console.log(payload);
    }

    function editItem() {
        const payload = { 
            ...selectedInventoryItem, 
            price: editInventoryItem.price,
            productId: editInventoryItem.productId,
            quantity: editInventoryItem.quantity
        };

        console.log("New edited item: ", payload);
    }

    function createInventory() {
        const payload = { ...inventoryCreation };

        console.log(payload);
    }

    function selectInventory(inv: InventoryDto) {
        setSelectedInventory(inv);
        setSelectedInventoryItem(null);
        setBEditItem(false);
        setEditInventoryItem({...inventoryItemCreationNew});
    }

    function selectInventoryItem(item: InventoryItemDto) {
        setSelectedInventoryItem(item);        
        setEditInventoryItem({price: item.price, productId: item.productId ?? 0, quantity: item.quantity});
    }

    return (
        <div className='content inventory'>
            <h3>Inventory</h3>

            <hr />

            <p>Manage and create inventory items.</p>

            <div className='card-body card'>
                <div className='inventory-items-category'>
                    <h4>Inventories</h4>

                    <ul className='inventory-list'>
                        {invs?.map((inv) => (
                            <li 
                                onClick={() => selectInventory(inv)}
                                className={`${selectedInventory?.id == inv.id 
                                    ? 'inventory-items-category-selected'
                                    : ''
                                }`}    
                            >{inv.name}</li>
                        ))}
                    </ul>

                    {/* <li className='inventory-items-category-selected'>Hardware</li> */}
                </div>

                <div className='inventory-items-list'>
                    {selectedInventory?.items.map((item) => (
                        <InventoryItemCard 
                            item={item} 
                            onClick={(selItem) => selectInventoryItem(selItem)}
                            // selectedItem={selectedInventoryItem}
                            onSelected={item.id == selectedInventoryItem?.id}
                        />
                    ))}

                    {selectedInventory?.items.length == 0 && 
                        <p>No items found.</p>
                    }

                    {!selectedInventory && 
                        <p>Select an inventory to view its items.</p>
                    }
                </div>
            </div>

            <div className='actions-bar'>
                <button onClick={() => changeView("a")} disabled={selectedInventory == null}>New Item</button>
                <button onClick={() => changeView("b")} disabled={selectedInventoryItem == null}>Edit Item</button>

                <button onClick={() => changeView("c")}>New Inventory</button>
            </div>

            <div className={`actions-info card ${bEditItem ? 'editing' : ''}`}>

                {bNewItem &&
                    <div>
                        <h4>New Inventory Item in {selectedInventory?.name}</h4>

                        <InputForm
                            field='Product:'
                            type='text'
                            name='productId'
                            value={inventoryItemCreation.productId}
                            options={(prods ?? []).map((p) => ({ value: p.id, label: p.name }))}
                            onChange={handleInventoryItem}
                        />

                        <InputForm
                            field='Stock quantity:'
                            type='number'
                            name='quantity'
                            value={inventoryItemCreation.quantity}
                            onChange={handleInventoryItem}
                        />

                        <InputForm
                            field='Price:'
                            type='number'
                            name='price'
                            value={inventoryItemCreation.price}
                            onChange={handleInventoryItem}
                        />

                        <button onClick={createItem}>Add item</button>
                    </div>
                }

                {bEditItem &&
                    <div>
                        <h4>Editing {selectedInventoryItem?.id} in {selectedInventory?.name}</h4>

                        <InputForm
                            field='Product:'
                            type='text'
                            name='productId'
                            value={editInventoryItem.productId}
                            options={(prods ?? []).map((p) => ({ value: p.id, label: p.name }))}
                            onChange={handleEditInventoryItem}
                        />

                        <InputForm
                            field='Stock quantity:'
                            type='number'
                            name='quantity'
                            value={editInventoryItem.quantity}
                            onChange={handleEditInventoryItem}
                        />

                        <InputForm
                            field='Price:'
                            type='number'
                            name='price'
                            value={editInventoryItem.price}
                            onChange={handleEditInventoryItem}
                        />

                        <button onClick={editItem}>Edit item</button>
                    </div>
                }

                {bNewInventory &&
                    <div>
                        <h4>New Inventory</h4>

                        <InputForm
                            field='Inventory Name:'
                            type='text'
                            name='name'
                            value={inventoryCreation.name}
                            onChange={handleInventoryCreation}
                        />

                        <InputForm
                            field='Location:'
                            type='text'
                            name='location'
                            value={inventoryCreation.location}
                            onChange={handleInventoryCreation}
                        />

                        <button onClick={createInventory}>Create inventory</button>
                    </div>
                }

                {!bNewInventory && !bEditItem && !bNewItem &&
                    <div>
                        <h4>Select an item or create one</h4>
                    </div>
                }

            </div>
        </div>
    )
}

export default Inventory