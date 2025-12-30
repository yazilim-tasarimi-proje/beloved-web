import React, { useEffect, useState } from "react";
import axios from "axios";
import "../css/Home.css";
import Topbar from "./Topbar";
import Navbar from "./Navbar";

const Home = () => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await axios.get("http://localhost:8080/products/list");
        setProducts(response.data);
      } catch (error) {
        console.error("Hata:", error);
      }
    };
    fetchProducts();
  }, []);

  return (
    <div className="home-wrapper">
      <Navbar />
      
      <div className="home-container">
        <h1 className="home-title">🎁 Özel Hediye Koleksiyonu</h1>
        
        <div className="product-list">
          {products.map((product) => (
            <div key={product.id} className="product-card">
              <div className="product-image-wrap">
 <img 
  // URL'yi oluştururken hem PUBLIC_URL kullanıp hem de başına / ekleyerek deneyin
  src={`${process.env.PUBLIC_URL}/images/${product.image_url}`} 
  alt={product.name} 
  className="product-card-img"
  onError={(e) => { 
    e.target.onerror = null; 
    // Eğer resim yine de yüklenemezse gri kutu kalmaya devam eder
    e.target.src = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=";
    e.target.style.backgroundColor = "#e0e0e0"; 
  }}
/>
              </div>
              
              <div className="product-info">
                <h3>{product.name}</h3>
                <p className="price-tag">{product.price} TL</p>
                <button className="buy-btn">Sepete Ekle</button>
              </div>
            </div>
          ))}
        </div>
      </div>

      <Topbar />
    </div>
  );
};

export default Home;