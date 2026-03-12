import React, { useState, type ChangeEvent } from 'react'
import "../style/inventory.css"
import type { ProductDto } from '../types/ProductDto';
import type { OrderItemDto } from '../types/OrderItemDto';
import { OrderItemNew } from '../new/OrderItemDto';
import type { InventoryItemDto } from '../types/InventoryItemDto';
import { inventoryItemNew } from '../new/InventoryItemDto';
import BigNumber from 'bignumber.js';

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

function Inventory() {
    // const [ orderItem, setOrderItem ] = useState<OrderItemDto>(OrderItemNew);
    const [inventoryItem, setInventoryItem] = useState<InventoryItemDto>(inventoryItemNew);

    const [bNewInventory, setBNewInventory] = useState<boolean>(false);
    const [bNewItem, setBNewItem] = useState<boolean>(false);
    const [bEditItem, setBEditItem] = useState<boolean>(false);

    const handleFormChange = (e:
        React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement | null>
    ) => {
        const { name, value } = e.target;

        const newValue = value ? new BigNumber(value) : new BigNumber(0);

        setInventoryItem({
            ...inventoryItem,
            [name]: newValue,
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
        const payload = { ...inventoryItem };

        console.log(payload);
    }

    return (
        <div className='content'>
            <h3>Inventory</h3>

            <hr />

            <p>Manage and create inventory items.</p>

            <div className='inventory-items-body card'>
                <div className='inventory-items-category'>
                    <ul>
                        <li>Peripherals</li>
                        <li className='inventory-items-category-selected'>Hardware</li>
                    </ul>
                </div>

                <div className='inventory-items-list'>
                    items list
                </div>
            </div>

            <div className='inventory-actions'>
                <button onClick={() => changeView("a")}>New Item</button>
                <button onClick={() => changeView("b")}>Edit Item</button>

                <button onClick={() => changeView("c")}>New Inventory</button>
            </div>

            <div className='card'>

                {bNewItem &&
                    <div>
                        <h4>New Inventory Item</h4>

                        <div>
                            <label htmlFor="product">Product:</label>
                            <input
                                id="prod"
                                list="prod-list"
                                name='productId'
                                value={inventoryItem.productId ?? ''}
                                onChange={handleFormChange}
                                placeholder="Select..."
                            />
                            <datalist id="prod-list">
                                {prods.map((prod) => (
                                    <option key={prod.id} value={prod.name} /> // O valor exibido nas opções do datalist
                                ))}
                            </datalist>
                        </div>

                        <div>
                            <label htmlFor="quantity">Stock quantity:</label>
                            <input type="number" name='quantity' value={inventoryItem.quantity} onChange={handleFormChange} />
                        </div>

                        <div>
                            <label htmlFor="price">Set price:</label>
                            <input type="number" name='price' value={inventoryItem.price.toString()} onChange={handleFormChange} />
                        </div>

                        <button onClick={createItem}>Add item</button>
                    </div>
                }

                {bNewInventory &&
                    <div>
                        <h4>New Inventory</h4>

                        <div>
                            <label htmlFor="product">Product:</label>
                            <input
                                id="prod"
                                list="prod-list"
                                name='productId'
                                value={inventoryItem.productId ?? ''}
                                onChange={handleFormChange}
                                placeholder="Select..."
                            />
                            <datalist id="prod-list">
                                {prods.map((prod) => (
                                    <option key={prod.id} value={prod.name} /> // O valor exibido nas opções do datalist
                                ))}
                            </datalist>
                        </div>

                        <div>
                            <label htmlFor="quantity">Stock quantity:</label>
                            <input type="number" name='quantity' value={inventoryItem.quantity} onChange={handleFormChange} />
                        </div>

                        <div>
                            <label htmlFor="price">Set price:</label>
                            <input type="number" name='price' value={inventoryItem.price.toString()} onChange={handleFormChange} />
                        </div>

                        <button onClick={createItem}>Add item</button>
                    </div>
                }

            </div>
        </div>
    )
}

export default Inventory