import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../css/Product.css';
import '../css/Home.css'; // Navbar ve Topbar stilleri için Home.css eklendi
import Navbar from '../home/Navbar';
import Topbar from '../home/Topbar';

const Product = () => {
    const [products, setProducts] = useState([]);
    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(true);
    
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

    const applyFilters = async () => {
        setLoading(true);
        try {
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

    // ... (import kısımları aynı)

    return (
        <div className="main-layout-wrapper">
            <Navbar /> {/* Üst Menü */}
            
            <div className="product-page">
                {/* Filtreleme Paneli */}
                <aside className="filter-sidebar">
                    <h3>Filtrele</h3>
                    <div className="filter-group">
                        <label>Kategori</label>
                        <select name="categoryId" className="filter-select" onChange={handleFilterChange}>
                            <option value="">Tüm Kategoriler</option>
                            {categories.map(cat => (
                                <option key={cat.id} value={cat.id}>{cat.name}</option>
                            ))}
                        </select>
                    </div>

                    <div className="filter-group checkbox-wrapper">
                        <input type="checkbox" name="personalized" id="pers" onChange={handleFilterChange} />
                        <label htmlFor="pers">Kişiselleştirilebilir</label>
                    </div>

                    <button className="filter-btn" onClick={applyFilters}>Uygula</button>
                </aside>

                {/* Ürün Listesi */}
                <main className="product-grid">
                    {products.length > 0 ? products.map(product => (
                        <div key={product.id} className="product-card-item">
                            {/* Kart İçeriği ... */}
                        </div>
                    )) : (
                        <div className="no-products-container">
                            <p>Aradığınız kriterlere uygun ürün bulunamadı.</p>
                        </div>
                    )}
                </main>
            </div>

            {/* Topbar'ı en alta aldık */}
            <Topbar /> 
        </div>
    );
};

export default Product;