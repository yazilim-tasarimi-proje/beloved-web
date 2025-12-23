import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../css/Product.css';

const Product = () => {
    const [products, setProducts] = useState([]);
    const [categories, setCategories] = useState([]); // Filtreleme için kategoriler
    const [loading, setLoading] = useState(true);
    
    // Filtreleme State'i (ProductFilterDto ile uyumlu)
    const [filters, setFilters] = useState({
        categoryId: '',
        personalized: null,
        productTypes: []
    });

    useEffect(() => {
        fetchInitialData();
    }, []);

    const fetchInitialData = async () => {
        try {
            // Paralel istekler: Ürünler ve Kategoriler
            const [prodRes, catRes] = await Promise.all([
                axios.get('http://localhost:8080/api/products/list'),
                axios.get('http://localhost:8080/api/categories/list')
            ]);
            setProducts(prodRes.data);
            setCategories(catRes.data);
            setLoading(false);
        } catch (error) {
            console.error("Veri yüklenirken hata oluştu:", error);
            setLoading(false);
        }
    };

    // Filtreleme isteği gönderen fonksiyon
    const applyFilters = async () => {
        setLoading(true);
        try {
            // Backend'deki filterProducts endpoint'ine POST isteği
            const response = await axios.post('http://localhost:8080/api/products/filter', filters);
            setProducts(response.data);
        } catch (error) {
            console.error("Filtreleme hatası:", error);
        }
        setLoading(false);
    };

    const handleFilterChange = (e) => {
        const { name, value, type, checked } = e.target;
        if (type === 'checkbox' && name === 'personalized') {
            setFilters({ ...filters, personalized: checked ? true : null });
        } else {
            setFilters({ ...filters, [name]: value });
        }
    };

    if (loading) return <div className="loader">Ürünler Hazırlanıyor...</div>;

    return (
        <div className="product-page">
            {/* Filtreleme Paneli */}
            <aside className="filter-sidebar">
                <h3>Filtrele</h3>
                <div className="filter-group">
                    <label>Kategori</label>
                    <select name="categoryId" onChange={handleFilterChange}>
                        <option value="">Tüm Kategoriler</option>
                        {categories.map(cat => (
                            <option key={cat.id} value={cat.id}>{cat.name}</option>
                        ))}
                    </select>
                </div>

                <div className="filter-group checkbox-group">
                    <input 
                        type="checkbox" 
                        name="personalized" 
                        id="pers" 
                        onChange={handleFilterChange} 
                    />
                    <label htmlFor="pers">Kişiselleştirilebilir</label>
                </div>

                <button className="filter-btn" onClick={applyFilters}>Uygula</button>
            </aside>

            {/* Ürün Listesi */}
            <main className="product-grid">
                {products.length > 0 ? products.map(product => (
                    <div key={product.id} className="product-card">
                        <div className="product-image">
                            <img src={product.imageUrl || 'https://via.placeholder.com/200'} alt={product.name} />
                            {product.personalized && <span className="badge">Kişiye Özel</span>}
                        </div>
                        <div className="product-info">
                            <h4>{product.name}</h4>
                            <p className="desc">{product.description}</p>
                            <div className="product-footer">
                                <span className="price">{product.price} TL</span>
                                <button className="add-to-cart">Sepete Ekle</button>
                            </div>
                        </div>
                    </div>
                )) : <p className="no-products">Aradığınız kriterlere uygun ürün bulunamadı.</p>}
            </main>
        </div>
    );
};

export default Product;