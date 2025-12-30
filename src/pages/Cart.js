import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../css/Cart.css';
import '../css/Home.css'; // Navbar ve Topbar stilleri için
import Navbar from '../home/Navbar';
import Topbar from '../home/Topbar';

const Cart = () => {
    const [cart, setCart] = useState(null);
    const [loading, setLoading] = useState(true);
    const [, setError] = useState('');

    const fetchCart = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get('http://localhost:8080/api/cart', {
                headers: { Authorization: `Bearer ${token}` }
            });
            setCart(response.data);
            setLoading(false);
        } catch (err) {
            setError('Sepet yüklenirken bir hata oluştu.');
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchCart();
    }, []);

    const handleRemove = async (productId) => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.delete(`http://localhost:8080/api/cart/remove/${productId}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setCart(response.data);
        } catch (err) {
            alert('Ürün sepetten çıkarılamadı.');
        }
    };

    const updateQuantity = async (productId, currentQty, delta) => {
        if (currentQty + delta <= 0) return;
        try {
            const token = localStorage.getItem('token');
            const response = await axios.post('http://localhost:8080/api/cart/add', 
                [{ productId, quantity: delta }], 
                { headers: { Authorization: `Bearer ${token}` } }
            );
            setCart(response.data);
        } catch (err) {
            console.error("Miktar güncellenemedi");
        }
    };

    if (loading) return (
        <div className="main-layout-wrapper">
            <Navbar />
            <div className="cart-status">Sepetiniz kontrol ediliyor...</div>
            <Topbar />
        </div>
    );

    if (!cart || cart.cartItems.length === 0) {
        return (
            <div className="main-layout-wrapper">
                <Navbar />
                <div className="empty-cart">
                    <i className="fas fa-shopping-basket"></i>
                    <h2>Sepetiniz şu an boş.</h2>
                    <button onClick={() => window.location.href='/products'} className="shop-btn">Alışverişe Başla</button>
                </div>
                <Topbar />
            </div>
        );
    }

    return (
        <div className="main-layout-wrapper">
            <Navbar /> {/* Üstte Navbar */}
            
            <div className="cart-container">
                <h1 className="cart-header">Alışveriş Sepetim</h1>
                
                <div className="cart-content">
                    <div className="cart-items-list">
                        {Array.from(cart.cartItems).map((item) => (
                            <div key={item.id} className="cart-item-card">
                                <div className="item-details">
                                    <h3>{item.productName}</h3>
                                    <p className="unit-price">Birim Fiyat: {item.productPrice} TL</p>
                                </div>
                                
                                <div className="item-actions">
                                    <div className="quantity-controls">
                                        <button onClick={() => updateQuantity(item.productId, item.quantity, -1)}>-</button>
                                        <span>{item.quantity}</span>
                                        <button onClick={() => updateQuantity(item.productId, item.quantity, 1)}>+</button>
                                    </div>
                                    <p className="item-total-price">
                                        {(item.productPrice * item.quantity).toFixed(2)} TL
                                    </p>
                                    <button className="remove-btn" onClick={() => handleRemove(item.productId)}>
                                        Kaldır
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>

                    <aside className="cart-summary">
                        <h2>Sipariş Özeti</h2>
                        <div className="summary-row">
                            <span>Ara Toplam:</span>
                            <span>{cart.totalPrice} TL</span>
                        </div>
                        <div className="summary-row">
                            <span>İndirim:</span>
                            <span className="discount">- {cart.discount} TL</span>
                        </div>
                        <hr />
                        <div className="summary-row total">
                            <span>Toplam:</span>
                            <span>{cart.discountPrice} TL</span>
                        </div>
                        <button className="checkout-btn">Ödeme Adımına Geç</button>
                    </aside>
                </div>
            </div>

            <Topbar /> {/* Altta Topbar */}
        </div>
    );
};

export default Cart;