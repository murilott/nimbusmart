import React, { useState } from 'react'
import { useParams } from 'react-router-dom';
import type { ProductDto } from '../types/ProductDto';
import { productDtoNew } from '../new/ProductDto';
import { useQuery } from '@tanstack/react-query';


function ProductPage() {
    // const [product, setProduct] = useState<ProductDto>(productDtoNew);
    const { id } = useParams();

    const { product, isLoading, isError, error } = useQuery({
        queryKey: ['product', id], // 2. O ID na Key força o refetch se a URL mudar
        // queryFn: () => fetchProduct(id), // 3. Passa o ID para a função de fetch
        enabled: !!id, // 4. Só executa se o ID existir (boa prática)
    });

    if (isLoading) return <div>Carregando...</div>;
    if (isError) return <div>Erro: {error.message}</div>;

    return (
        <div className='content'>
            <div>
                <h3>{product?.name}</h3>
            </div>

            <div>
                <img className='product-card-img' src="https://placehold.co/600x800" alt="Product image" width="600" height="800" />
            </div>

            <div>
                <div>
                    <h4>Description</h4>

                    <p>{product.description}</p>
                </div>

                <div>
                    <p>Price: R% 49,99</p>
                    <div>
                        <label htmlFor="quantidade">Quantidade</label>
                        <input type="number" />
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ProductPage