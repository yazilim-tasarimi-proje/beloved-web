import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../css/Address.css';

const Address = () => {
    const [addresses, setAddresses] = useState([]);
    const [formData, setFormData] = useState({
        addressId: null,
        street: '',
        city: '',
        state: '',
        postalCode: '',
        country: '',
        phone: ''
    });
    const [isEditing, setIsEditing] = useState(false);
    const [message, setMessage] = useState('');

    // Backend'den adresleri çekme
    const fetchAddresses = async () => {
        try {
            // Not: Auth token'ınızın localstorage'da olduğunu varsayıyorum
            const token = localStorage.getItem('token');
            const response = await axios.get('http://localhost:8080/api/addresses', {
                headers: { Authorization: `Bearer ${token}` }
            });
            setAddresses(response.data);
        } catch (error) {
            console.error("Adresler yüklenirken hata oluştu:", error);
        }
    };

    useEffect(() => {
        fetchAddresses();
    }, []);

    const handleInputChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem('token');
        try {
            if (isEditing) {
                // updateAddress endpoint'i
                await axios.put('http://localhost:8080/api/addresses/update', formData, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setMessage('Adres başarıyla güncellendi.');
            } else {
                // addAddress endpoint'i
                await axios.post('http://localhost:8080/api/addresses/add', formData, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setMessage('Adres başarıyla eklendi.');
            }
            clearForm();
            fetchAddresses();
        } catch (error) {
            setMessage('İşlem sırasında bir hata oluştu.');
        }
    };

    const handleEdit = (addr) => {
        setFormData({ ...addr, addressId: addr.addressId });
        setIsEditing(true);
        window.scrollTo(0, 0);
    };

    const handleDelete = async (id) => {
        if (window.confirm("Bu adresi silmek istediğinize emin misiniz?")) {
            const token = localStorage.getItem('token');
            try {
                await axios.delete(`http://localhost:8080/api/addresses/delete/${id}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                fetchAddresses();
                setMessage('Adres silindi.');
            } catch (error) {
                setMessage('Silme işlemi başarısız.');
            }
        }
    };

    const clearForm = () => {
        setFormData({ addressId: null, street: '', city: '', state: '', postalCode: '', country: '', phone: '' });
        setIsEditing(false);
    };

    return (
        <div className="address-container">
            <h2>{isEditing ? 'Adresi Güncelle' : 'Yeni Adres Ekle'}</h2>
            {message && <div className="alert">{message}</div>}
            
            <form className="address-form" onSubmit={handleSubmit}>
                <div className="form-group">
                    <input name="street" placeholder="Sokak/Cadde" value={formData.street} onChange={handleInputChange} required />
                    <input name="city" placeholder="Şehir" value={formData.city} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                    <input name="state" placeholder="Bölge/Eyalet" value={formData.state} onChange={handleInputChange} required />
                    <input name="postalCode" placeholder="Posta Kodu" value={formData.postalCode} onChange={handleInputChange} required />
                </div>
                <div className="form-group">
                    <input name="country" placeholder="Ülke" value={formData.country} onChange={handleInputChange} required />
                    <input name="phone" placeholder="Telefon" value={formData.phone} onChange={handleInputChange} required />
                </div>
                <div className="form-buttons">
                    <button type="submit" className="btn-save">{isEditing ? 'Güncelle' : 'Kaydet'}</button>
                    {isEditing && <button type="button" className="btn-cancel" onClick={clearForm}>İptal</button>}
                </div>
            </form>

            <hr />

            <h3>Kayıtlı Adreslerim</h3>
            <div className="address-list">
                {addresses.length === 0 ? <p>Henüz bir adres eklemediniz.</p> : addresses.map(addr => (
                    <div key={addr.addressId} className="address-card">
                        <div className="address-info">
                            <strong>{addr.street}</strong>
                            <p>{addr.city}, {addr.state} {addr.postalCode}</p>
                            <p>{addr.country} | Tel: {addr.phone}</p>
                        </div>
                        <div className="card-actions">
                            <button onClick={() => handleEdit(addr)} className="btn-edit">Düzenle</button>
                            <button onClick={() => handleDelete(addr.addressId)} className="btn-delete">Sil</button>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Address;