import React from 'react'
import '../style/header.css'
import HeaderLogo from './HeaderLogo'
import { Link } from 'react-router-dom'

function Header() {
    return (
        <div className='header'>
            <div className='header-content'>
                <h3><HeaderLogo /></h3>

                <ul className='font-big'>
                    <li><Link to="/">Home</Link></li>
                    <li><Link to="/inventory">Inventory</Link></li>
                    <li><Link to="/delivery">Delivery</Link></li>
                    <li><Link to="/product">Product</Link></li>
                    <li><Link to="/payment">Payment</Link></li>
                </ul>

                <span className='header-cart font-big'><Link to="/cart">Cart</Link></span>
            </div>
        </div>
    )
}

export default Header