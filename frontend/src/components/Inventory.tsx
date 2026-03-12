import React from 'react'
import "../style/inventory.css"

function Inventory() {
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

            <button>New Item</button>
            <button>Edit Item</button>

            <button>New Inventory</button>

            <div className='card'>
                <h4>New Inventory Item</h4>
            </div>
        </div>
    )
}

export default Inventory