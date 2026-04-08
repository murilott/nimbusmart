import React, { useState } from 'react'
import type { ProductDto } from '../types/ProductDto';
import type { ProductCreationRequest } from '../dto/request/ProductCreationRequest';
import { productCreationNew } from '../new/request/ProductCreationRequest';
import InputForm from './InputForm';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { createProduct, listProducts } from '../service/product.service';

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
    {
        id: 4,
        name: "Mouse",
        description: "Mouse Logitech",
        tags: [],
        image: ""
    },
    {
        id: 5,
        name: "Teclado",
        description: "Teclado Logitech aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
        tags: [],
        image: ""
    },
    {
        id: 6,
        name: "Pendrive",
        description: "Pendrive Logitech",
        tags: [],
        image: ""
    },
    {
        id: 7,
        name: "Mouse",
        description: "Mouse Logitech",
        tags: [],
        image: ""
    },
    {
        id: 8,
        name: "Teclado",
        description: "Teclado Logitech aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
        tags: [],
        image: ""
    },
    {
        id: 9,
        name: "Pendrive",
        description: "Pendrive Logitech",
        tags: [],
        image: ""
    },
]

function Product() {
    const [productCreation, setProductCreation] = useState<ProductCreationRequest>(productCreationNew);
    const [selectedProduct, setSelectedProduct] = useState<ProductDto | null>(null);

    const [editProduct, setEditProduct] = useState<ProductCreationRequest>(productCreationNew);

    const [bNewProduct, setBNewProduct] = useState<boolean>(false);
    const [bEditProduct, setBEditProduct] = useState<boolean>(false);

    const queryClient = useQueryClient();

    function changeView(opt: string) {
        if (opt === "a") {
            setBNewProduct(true);
            setBEditProduct(false);
        }
        if (opt === "b") {
            setBNewProduct(false);
            setBEditProduct(true);
        }
    }

    const handleProductCreation = (e:
        React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement | null>
    ) => {
        const { name, value } = e.target;

        setProductCreation((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleProductEdit = (e:
        React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement | null>
    ) => {
        const { name, value } = e.target;

        setEditProduct((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const { mutateAsync, isPending } = useMutation({
        mutationFn: createProduct,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['products'] });
        },
    });

    async function creatingProduct() {
        // const newTagList: string[] = productCreation.tags.split(",").map(item => item.trim().toLowerCase());
        // tags: newTagList

        const payload: ProductCreationRequest = { ...productCreation };

        try {
            const response = await mutateAsync(payload);
            setProductCreation({ ...productCreationNew });
        } catch (err: unknown) {
            console.error("Unexpected error:", err);
        }
    }

    function editingProduct() {
        const payload = { ...editProduct };

        console.log(payload);
    }

    function selectProduct(product: ProductDto) {
        setSelectedProduct(product);
        setEditProduct({ name: product.name, description: product.description, image: product.image, tags: product.tags.toString() });
        console.log(product);        
    }

    const { isLoading, error, data: products } = useQuery<ProductDto[]>({
        queryKey: ['products'],
        queryFn: listProducts,
    });

    return (
        <div className='content'>
            <h3>Products</h3>

            <p>Manage and create products.</p>

            <div className='card card-body table-wrapper'>
                {((products ?? []).length == 0 && !isLoading && !error) ?
                    <p>No Products found.</p>
                    :
                    <table className='products-table'>
                        <colgroup>
                            <col style={{ width: '50px' }} />
                            <col style={{ width: '110px' }} />
                        </colgroup>
                        <thead>
                            <tr style={{ textAlign: 'left', borderBottom: '2px solid #ddd' }}>
                                <th>ID</th>
                                <th>Image</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Tags</th>
                            </tr>
                        </thead>
                        <tbody>
                            {products?.map((product) => (
                                <tr
                                    key={product.id}
                                    className={`${product.id == selectedProduct?.id ? 'products-table-selected' : ''}`}
                                    onClick={() => selectProduct(product)}
                                >
                                    <td>{product.id}</td>
                                    <td>
                                        {product.image ? (
                                            <img src={product.image} alt={product.name} width="40" />
                                        ) : (
                                            "No image"
                                        )}
                                    </td>
                                    <td>{product.name}</td>
                                    <td>{product.description}</td>
                                    <td>
                                        {product.tags.value.join(",")}
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                }
            </div>

            <div className='actions-bar'>
                <button onClick={() => changeView("a")} >New Product</button>
                {/* <button onClick={() => changeView("b")} disabled={selectedProduct == null}>Edit Product</button>
                <button onClick={() => changeView("c")}>Delete Product</button> */}
            </div>

            <div className={`actions-info card ${bEditProduct ? 'editing' : ''}`}>
                {bNewProduct &&
                    <div>
                        <h4>New Product</h4>

                        <InputForm
                            field='Name:'
                            type='text'
                            name='name'
                            value={productCreation.name}
                            onChange={handleProductCreation}
                        />

                        <InputForm
                            field='Description:'
                            type='textarea'
                            name='description'
                            value={productCreation.description}
                            onChange={handleProductCreation}
                        />

                        <InputForm
                            field='Image:'
                            type='text'
                            name='image'
                            value={productCreation.image}
                            onChange={handleProductCreation}
                        />

                        <InputForm
                            field='Tags:'
                            type='text'
                            name='tags'
                            placeholder='tag1,tag2,tag3'
                            value={productCreation.tags}
                            onChange={handleProductCreation}
                        />

                        <button onClick={creatingProduct}>Add item</button>
                    </div>
                }

                {/* {bEditProduct &&
                    <div>
                        <h4>Editing {selectedProduct?.name}</h4>

                        <InputForm
                            field='Name:'
                            type='text'
                            name='name'
                            value={editProduct.name}
                            onChange={handleProductEdit}
                        />

                        <InputForm
                            field='Description:'
                            type='textarea'
                            name='description'
                            value={editProduct.description}
                            onChange={handleProductEdit}
                        />

                        <InputForm
                            field='Image:'
                            type='text'
                            name='image'
                            value={editProduct.image}
                            onChange={handleProductEdit}
                        />

                        <InputForm
                            field='Tags:'
                            type='text'
                            name='tags'
                            value={editProduct.tags}
                            onChange={handleProductEdit}
                        />

                        <button onClick={editingProduct}>Edit item</button>
                    </div>
                } */}

                {/* {!bNewProduct && !bEditProduct &&
                    <div>
                        <h4>Select an product or create one</h4>
                    </div>
                } */}

            </div>
        </div>
    )
}

export default Product