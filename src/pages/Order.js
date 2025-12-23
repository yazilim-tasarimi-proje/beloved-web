import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../css/Order.css';

const Order = () => {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState('');

    useEffect(() => {
        fetchOrders();
    }, []);

    const fetchOrders = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get('http://localhost:8080/api/orders/user', {
                headers: { Authorization: `Bearer ${token}` }
            });
            // Tarihe göre en yeni siparişi en üstte göster
            const sortedOrders = response.data.sort((a, b) => 
                new Date(b.orderDate) - new Date(a.orderDate)
            );
            setOrders(sortedOrders);
            setLoading(false);
        } catch (error) {
            console.error("Siparişler yüklenirken hata oluştu:", error);
            setLoading(false);
        }
    };

    const handleCancelOrder = async (orderId) => {
        if (window.confirm("Bu siparişi iptal etmek istediğinize emin misiniz?")) {
            try {
                const token = localStorage.getItem('token');
                await axios.delete(`http://localhost:8080/api/orders/cancel/${orderId}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setMessage('Sipariş başarıyla iptal edildi.');
                fetchOrders(); // Listeyi güncelle
            } catch (error) {
                alert("Sipariş iptal edilemedi.");
            }
        }
    };

    const formatDate = (dateString) => {
        const options = { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' };
        return new Date(dateString).toLocaleDateString('tr-TR', options);
    };

    if (loading) return <div className="order-loader">Sipariş geçmişiniz yükleniyor...</div>;

    return (
        <div className="orders-page-container">
            <h1 className="page-title">Siparişlerim</h1>
            
            {message && <div className="order-alert">{message}</div>}

            {orders.length === 0 ? (
                <div className="no-orders">
                    <p>Henüz bir siparişiniz bulunmuyor.</p>
                </div>
            ) : (
                <div className="orders-list">
                    {orders.map((order) => (
                        <div key={order.orderId} className="order-card">
                            <div className="order-header">
                                <div className="order-meta">
                                    <span className="order-id">Sipariş No: #{order.orderId}</span>
                                    <span className="order-date">{formatDate(order.orderDate)}</span>
                                </div>
                                <div className="order-total">
                                    Toplan Tutar: <strong>{order.totalPrice.toLocaleString('tr-TR')} TL</strong>
                                </div>
                            </div>

                            <div className="order-items">
                                <h4>Sipariş İçeriği ({order.items.length} Ürün)</h4>
                                <ul>
                                    {Array.from(order.items).map((item, index) => (
                                        <li key={index} className="order-item">
                                            <span className="item-name">{item.productName}</span>
                                            <span className="item-qty">Adet: {item.quantity}</span>
                                        </li>
                                    ))}
                                </ul>
                            </div>

                            <div className="order-footer">
                                <button 
                                    className="btn-cancel-order" 
                                    onClick={() => handleCancelOrder(order.orderId)}
                                >
                                    Siparişi İptal Et
                                </button>
                                <span className="order-status-badge">Hazırlanıyor</span>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default Order;