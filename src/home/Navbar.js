import React from "react";
import { Link } from "react-router-dom";
import "../css/Home.css";
import logo from "../assets/1.png";

const Navbar = () => {
    return (
      <nav className="navbar">
        {/* SOL MENÜ */}
        <ul className="navbar-links left-side">
            <li><Link to="/home">Anasayfa</Link></li>
            <li><Link to="/products">Ürünler</Link></li>
            <li><Link to="/login">Çıkış Yap</Link></li>
        </ul>

        {/* LOGO */}
        <div className="navbar-logo">
            <img src={logo} alt="Logo" className="nav-logo-img" />
        </div>

        {/* SAĞ MENÜ */}
        <ul className="navbar-links right-side">
            <li><Link to="/cart">Sepetim</Link></li>
            <li><Link to="/favorites">Favoriler</Link></li>
            <li><Link to="/profile">Profilim</Link></li>
        </ul>
      </nav>
    );
};

export default Navbar;