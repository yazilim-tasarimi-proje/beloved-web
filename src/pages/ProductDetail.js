import React from 'react';
import Review from './Review';

const ProductDetail = ({ product }) => {
    // Eğer product prop olarak gelmiyorsa (undefined ise) hata almamak için kontrol:
    if (!product) return <div>Ürün yükleniyor...</div>;

    return (
        <div className="product-detail-page">
            <h1>{product.name}</h1>
            <p>{product.description}</p>
            {/* Alt kısma yorumları ekliyoruz */}
            <Review productId={product.id} />
        </div>
    );
};

// BU SATIR EKSİK OLDUĞU İÇİN HATA ALIYORDUN:
export default ProductDetail;