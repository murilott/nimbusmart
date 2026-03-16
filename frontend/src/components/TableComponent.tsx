// import React from 'react'
// import type { Column } from '../types/Column';

// interface TableComponentProps<T> {
//     item: T,
//     itemList: T[],
//     selectedItem: (item: T) => void;
//     cols: Column<T>[];
// }

// function TableComponent<T>({ item, itemList, selectedItem, cols }: TableComponentProps<T>) {
//     return (
//         <div className='table-wrapper'>
//             <table className='products-table'>
//                 <colgroup>
//                     <col style={{ width: '50px' }} />
//                     <col style={{ width: '110px' }} />
//                 </colgroup>
//                 <thead>
//                     <tr style={{ textAlign: 'left', borderBottom: '2px solid #ddd' }}>
//                         <th>ID</th>
//                         <th>Image</th>
//                         <th>Name</th>
//                         <th>Description</th>
//                         <th>Tags</th>
//                     </tr>
//                 </thead>
//                 <tbody>
//                     {prods.map((product) => (
//                         <tr
//                             key={product.id}
//                             className={`${product.id == selectedProduct?.id ? 'products-table-selected' : ''}`}
//                             onClick={() => selectProduct(product)}
//                         >
//                             <td>{product.id}</td>
//                             <td>
//                                 {product.image ? (
//                                     <img src={product.image} alt={product.name} width="40" />
//                                 ) : (
//                                     "No image"
//                                 )}
//                             </td>
//                             <td>{product.name}</td>
//                             <td>{product.description}</td>
//                             <td>
//                                 {product.tags.length > 0
//                                     ? product.tags.join(', ')
//                                     : '—'}
//                             </td>
//                         </tr>
//                     ))}
//                 </tbody>
//             </table>
//         </div>
//     )
// }

// export default TableComponent