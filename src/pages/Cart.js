import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../css/Cart.css';

const Cart = () => {
    const [cart, setCart] = useState(null);
    const [loading, setLoading] = useState(true);
    const [, setError] = useState('');

    // Sepet verilerini çekme
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

    // Sepetten ürün silme
    const handleRemove = async (productId) => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.delete(`http://localhost:8080/api/cart/remove/${productId}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setCart(response.data); // Backend güncel CartDto döndürdüğü için direkt set ediyoruz
        } catch (err) {
            alert('Ürün sepetten çıkarılamadı.');
        }
    };

    // Miktar güncelleme (Backend'de tekli ekleme metodunu miktar +1/-1 olarak kullanabilirsiniz)
    const updateQuantity = async (productId, currentQty, delta) => {
        if (currentQty + delta <= 0) return;
        
        try {
            const token = localStorage.getItem('token');
            const response = await axios.post('http://localhost:8080/api/cart/add', 
                [{ productId, quantity: delta }], // List<CartItemDto> beklediği için array yolluyoruz
                { headers: { Authorization: `Bearer ${token}` } }
            );
            setCart(response.data);
        } catch (err) {
            console.error("Miktar güncellenemedi");
        }
    };

    if (loading) return <div className="cart-status">Sepetiniz kontrol ediliyor...</div>;
    if (!cart || cart.cartItems.length === 0) {
        return (
            <div className="empty-cart">
                <i className="fas fa-shopping-basket"></i>
                <h2>Sepetiniz şu an boş.</h2>
                <button onClick={() => window.location.href='/products'}>Alışverişe Başla</button>
            </div>
        );
    }

    return (
        <div className="cart-container">
            <h1 className="cart-header">Alışveriş Sepetim</h1>
            
            <div className="cart-content">
                {/* Sol Kısım: Ürün Listesi */}
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

                {/* Sağ Kısım: Sipariş Özeti */}
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
    );
};

export default Cart;