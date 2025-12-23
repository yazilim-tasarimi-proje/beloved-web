import React from "react";
import { useNavigate } from "react-router-dom";
import "../css/Giris.css"; 
import logo from "../assets/1.png";

const Giris = () => {
  const navigate = useNavigate();

  return (
    <div className="giris-page-wrapper"> {/* Kapsayıcı eklendi */}
      <div className="home-container">
        <img src={logo} alt="Logo" className="home-logo" />
        <h1 className="home-title">Welcome to Beloved</h1>
        
        <div className="home-buttons">
          <button className="home-button" onClick={() => navigate("/login")}>
            Login
          </button>
          <button className="home-button" onClick={() => navigate("/register")}>
            Register
          </button>
        </div>
      </div>
    </div>
  );
};

export default Giris;