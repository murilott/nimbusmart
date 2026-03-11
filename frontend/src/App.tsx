import './App.css'
import '../src/style/base.css'
import { Route, Routes } from 'react-router-dom'
import Home from './components/Home'
import Header from './components/Header'
import Inventory from './components/Inventory'
import Delivery from './components/Delivery'
import Product from './components/Product'
import ProductPage from './components/ProductPage'

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
      </Routes>
    </div>
  )
}

export default App
