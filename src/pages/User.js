import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../css/User.css';
import Navbar from '../home/Navbar'; // Sol menü varsayımıyla
import Topbar from '../home/Topbar'; // Yeni eklenen bileşen

const User = () => {
    const [profile, setProfile] = useState({ firstName: '', lastName: '', email: '' });
    const [updateData, setUpdateData] = useState({ firstName: '', lastName: '', email: '' });
    const [passwordData, setPasswordData] = useState({ oldPassword: '', newPassword: '' });
    const [message, setMessage] = useState({ type: '', text: '' });
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchUserProfile();
    }, []);

    const fetchUserProfile = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get('http://localhost:8080/auth/profile', {
                headers: { Authorization: `Bearer ${token}` }
            });
            setProfile(response.data);
            setUpdateData({ 
                firstName: response.data.firstName, 
                lastName: response.data.lastName, 
                email: response.data.email 
            });
            setLoading(false);
        } catch (error) {
            console.error("Profil yüklenemedi", error);
            const errorMsg = error.response?.data?.message || error.response?.data || 'Profil yüklenemedi.';
            setMessage({ type: 'error', text: String(errorMsg) });
            setLoading(false);
        }
    };

    const handleUpdateInfo = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem('token');
            await axios.put('http://localhost:8080/auth/update-profile', updateData, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setMessage({ type: 'success', text: 'Bilgileriniz başarıyla güncellendi.' });
            fetchUserProfile(); 
        } catch (error) {
            const errorMsg = error.response?.data?.message || error.response?.data || 'Güncelleme hatası.';
            setMessage({ type: 'error', text: String(errorMsg) });
        }
    };

    const handleChangePassword = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem('token');
            await axios.put('http://localhost:8080/auth/change-password', passwordData, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setMessage({ type: 'success', text: 'Şifreniz başarıyla değiştirildi.' });
            setPasswordData({ oldPassword: '', newPassword: '' });
        } catch (error) {
            const errorMsg = error.response?.data?.message || error.response?.data || 'Şifre değiştirilemedi.';
            setMessage({ type: 'error', text: String(errorMsg) });
        }
    };

    if (loading) return <div className="user-loader">Yükleniyor...</div>;

    return (
        <div className="app-container"> {/* Tüm sayfayı kapsayan ana div */}
            <Navbar /> {/* Genelde sol tarafta sabitlenir */}
            
            <div className="main-content"> {/* Navbar dışındaki alanı yönetmek için */}
               

                <div className="user-profile-container">
                    <div className="profile-header">
                        <div className="avatar">
                            {profile.firstName?.charAt(0)}{profile.lastName?.charAt(0)}
                        </div>
                        <h1>{profile.firstName} {profile.lastName}</h1>
                        <p>{profile.email}</p>
                    </div>

                    {message.text && (
                        <div className={`alert ${message.type}`}>
                            {typeof message.text === 'string' ? message.text : 'Bir hata oluştu'}
                        </div>
                    )}

                    <div className="profile-grid">
                        <section className="profile-section">
                            <h3>Profil Bilgilerini Düzenle</h3>
                            <form onSubmit={handleUpdateInfo}>
                                <div className="input-group">
                                    <label>Ad</label>
                                    <input 
                                        type="text" 
                                        value={updateData.firstName} 
                                        onChange={(e) => setUpdateData({...updateData, firstName: e.target.value})}
                                    />
                                </div>
                                <div className="input-group">
                                    <label>Soyad</label>
                                    <input 
                                        type="text" 
                                        value={updateData.lastName} 
                                        onChange={(e) => setUpdateData({...updateData, lastName: e.target.value})}
                                    />
                                </div>
                                <div className="input-group">
                                    <label>E-posta (Değiştirilemez)</label>
                                    <input 
                                        type="email" 
                                        value={updateData.email} 
                                        readOnly
                                        style={{backgroundColor: '#f5f5f5', cursor: 'not-allowed'}}
                                    />
                                </div>
                                <button type="submit" className="btn-update">Bilgileri Kaydet</button>
                            </form>
                        </section>

                        <section className="profile-section">
                            <h3>Şifre Değiştir</h3>
                            <form onSubmit={handleChangePassword}>
                                <div className="input-group">
                                    <label>Mevcut Şifre</label>
                                    <input 
                                        type="password" 
                                        value={passwordData.oldPassword} 
                                        onChange={(e) => setPasswordData({...passwordData, oldPassword: e.target.value})}
                                        required
                                    />
                                </div>
                                <div className="input-group">
                                    <label>Yeni Şifre</label>
                                    <input 
                                        type="password" 
                                        value={passwordData.newPassword} 
                                        onChange={(e) => setPasswordData({...passwordData, newPassword: e.target.value})}
                                        required
                                    />
                                </div>
                                <button type="submit" className="btn-password">Şifreyi Güncelle</button>
                            </form>
                        </section>
                    </div>
                </div>
                <Topbar/>
            </div>
        </div>
    );
};

export default User;