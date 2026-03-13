import './App.css'
import '../src/style/base.css'
import '../src/style/card.css'
import '../src/style/table.css'
import { Route, Routes } from 'react-router-dom'
import Home from './components/Home'
import Header from './components/Header'
import Inventory from './components/Inventory'
import Delivery from './components/Delivery'
import Product from './components/Product'
import ProductPage from './components/ProductPage'
import Payment from './components/Payment'

function App() {
  return (
    <div className='app'>
      <Header />

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/inventory" element={<Inventory />} />
        <Route path="/delivery" element={<Delivery />} />
        <Route path="/product" element={<Product />} />
        <Route path="/product/:id" element={<ProductPage />} />
        <Route path="/payment" element={<Payment />} />
      </Routes>
    </div>
  )
}

export default App
