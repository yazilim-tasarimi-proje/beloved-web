import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import '../css/Review.css';

const Review = ({ productId }) => {
    const [reviews, setReviews] = useState([]);
    const [newComment, setNewComment] = useState("");
    const [rating, setRating] = useState(5);
    const [message, setMessage] = useState("");

    // fetchReviews fonksiyonunu useCallback ile sarmaladık (Eslint hook hatasını giderir)
    const fetchReviews = useCallback(async () => {
        try {
            const response = await axios.get(`http://localhost:8080/api/reviews/product/${productId}`);
            setReviews(response.data);
        } catch (error) {
            console.error("Yorumlar yüklenemedi", error);
        }
    }, [productId]);

    useEffect(() => {
        fetchReviews();
    }, [fetchReviews]);

    const handleSubmitReview = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem('token');
            const reviewDto = {
                productId: productId,
                comment: newComment,
                rating: rating
            };

            await axios.post('http://localhost:8080/api/reviews/add', reviewDto, {
                headers: { Authorization: `Bearer ${token}` }
            });

            setMessage("Yorumunuz başarıyla eklendi!");
            setNewComment("");
            setRating(5); // Puanı sıfırla
            fetchReviews(); // Listeyi güncelle
        } catch (error) {
            setMessage("Yorum eklenirken hata oluştu. Giriş yapmamış olabilirsiniz.");
        }
    };

    return (
        <div className="review-section">
            <h3>Müşteri Yorumları ({reviews.length})</h3>

            {/* Yeni Yorum Formu - Değişkenlerin ve set metotlarının kullanıldığı yer */}
            <form className="review-form" onSubmit={handleSubmitReview}>
                <textarea 
                    placeholder="Ürün hakkındaki düşüncelerinizi paylaşın..."
                    value={newComment} // newComment burada kullanıldı
                    onChange={(e) => setNewComment(e.target.value)} // setNewComment burada kullanıldı
                    required
                ></textarea>
                
                <div className="form-footer">
                    <div className="rating-select">
                        <label>Puan: </label>
                        <select 
                            value={rating} // rating burada kullanıldı
                            onChange={(e) => setRating(Number(e.target.value))} // setRating burada kullanıldı
                        >
                            {[5, 4, 3, 2, 1].map(num => (
                                <option key={num} value={num}>{num} Yıldız</option>
                            ))}
                        </select>
                    </div>
                    <button type="submit">Gönder</button>
                </div>
                {message && <p className="review-msg">{message}</p>} {/* message burada kullanıldı */}
            </form>

            {/* Yorum Listesi - reviews değişkeninin kullanıldığı yer */}
            <div className="reviews-list">
                {reviews.length > 0 ? (
                    reviews.map((rev) => (
                        <div key={rev.id} className="review-item">
                            <div className="review-header">
                                <span className="reviewer-name">{rev.userEmail}</span>
                                <span className="review-rating">{"⭐".repeat(rev.rating)}</span>
                            </div>
                            <p className="review-text">{rev.comment}</p>
                            <small className="review-date">
                                {new Date(rev.createdAt).toLocaleDateString()}
                            </small>
                        </div>
                    ))
                ) : (
                    <p>Henüz yorum yapılmamış. İlk yorumu siz yapın!</p>
                )}
            </div>
        </div>
    );
};

export default Review;