import React from 'react'
import '../style/header.css'

function Header() {
    return (
        <div className='header'>
            <div className='header-opts'>
                <h3>nimbusmart</h3>

                <ul className='font-big'>
                    <li><a href="/">Home</a></li>
                    <li><a href="/inventory">Inventory</a></li>
                    <li><a href="/delivery">Delivery</a></li>
                    <li><a href="/product">Product</a></li>
                </ul>
            </div>
        </div>
    )
}

export default Header