import React from 'react'
import "../style/home.css"
import ProductCard from './ProductCard'

function Home() {
  return (
    <div className='content'>
        <h3>Produtos</h3>

        <div>
            <label htmlFor="">Pesquisar:</label>
            <input type="text" />
        </div>

        <div>
            <div className='product-list'>
                <ProductCard name='Product' description='Description... aaaa. bbbbb cccc' price='R$ 49,99' />
                <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                <ProductCard name='Product' description='Description...' price='R$ 49,99' />
                <ProductCard name='Product' description='Description...' price='R$ 49,99' />
            </div>
        </div>
    </div>
  )
}

export default Home