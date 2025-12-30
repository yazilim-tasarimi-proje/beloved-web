import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import '../css/Favorite.css';
import '../css/Home.css'; // Navbar ve Topbar stilleri için
import Navbar from '../home/Navbar';
import Topbar from '../home/Topbar';

const Favorite = () => {
    const [favorites, setFavorites] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    const fetchFavorites = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get('http://localhost:8080/api/favorites/user', {
                headers: { Authorization: `Bearer ${token}` }
            });
            setFavorites(response.data);
            setLoading(false);
        } catch (err) {
            setError('Favoriler yüklenirken bir hata oluştu.');
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchFavorites();
    }, []);

    const handleRemoveFavorite = async (productId) => {
        try {
            const token = localStorage.getItem('token');
            await axios.delete(`http://localhost:8080/api/favorites/remove/${productId}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setFavorites(favorites.filter(fav => fav.productId !== productId));
        } catch (err) {
            alert('Favori silinirken bir hata oluştu.');
        }
    };

    if (loading) return (
        <div className="main-layout-wrapper">
            <Navbar />
            <div className="fav-loader">Favorileriniz yükleniyor...</div>
            <Topbar />
        </div>
    );

    return (
        <div className="main-layout-wrapper">
            <Navbar />
            
            <div className="favorite-page-container">
                <h2 className="fav-title">Favorilerim ({favorites.length})</h2>
                
                {error && <p className="fav-error">{error}</p>}

                {favorites.length === 0 ? (
                    <div className="empty-fav">
                        <p>Henüz favori ürününüz bulunmuyor.</p>
                        <Link to="/products" className="go-shopping-btn">Alışverişe Başla</Link>
                    </div>
                ) : (
                    <div className="fav-grid">
                        {favorites.map((fav) => (
                            <div key={fav.id} className="fav-card">
                                <div className="fav-image-wrapper">
                                    <img 
                                        src={`https://via.placeholder.com/200?text=Product+${fav.productId}`} 
                                        alt="Ürün" 
                                    />
                                    <button 
                                        className="remove-fav-btn" 
                                        onClick={() => handleRemoveFavorite(fav.productId)}
                                        title="Favorilerden Kaldır"
                                    >
                                        &times;
                                    </button>
                                </div>
                                <div className="fav-details">
                                    <h3>Ürün ID: #{fav.productId}</h3>
                                    <p>İncelemek için detaylara gidin.</p>
                                    <Link to={`/product/${fav.productId}`} className="view-detail-btn">
                                        Ürünü Gör
                                    </Link>
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </div>

            <Topbar />
        </div>
    );
};

export default Favorite;