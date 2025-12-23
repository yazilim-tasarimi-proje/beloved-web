// Home.js
import React from "react";
import "../css/Home.css";
import Topbar from "./Topbar";
import Navbar from "./Navbar";

const products = [
  { id: 1, name: "Kupa Bardak", price: "50 TL" },
  { id: 2, name: "Ayaklı Lamba", price: "150 TL" },
  { id: 3, name: "Dekoratif Yastık", price: "75 TL" },
  { id: 4, name: "Saat", price: "200 TL" },
  { id: 5, name: "Saat", price: "200 TL" },
  { id: 6, name: "Saat", price: "200 TL" }
];

const Home = () => {
  return (
   <div className="home-wrapper">
      <Navbar/>

    <div className="home-container">
      <h1 className="home-title">Hediye Eşyaları</h1>
      <div className="product-list">
        {products.map((product) => (
          <div key={product.id} className="product-card">
            <h3>{product.name}</h3>
            <p>{product.price}</p>
            <button>Satın Al</button>
          </div>
        ))}
      </div>
    </div>
 <Topbar/>
</div>
  );
};

export default Home;
