import React, { useState } from "react";
import axios from "axios";
import "../css/Login.css";
import logo from "../assets/1.png";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    email: "",
    password: ""
  });

  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // 🚀 1. Gelen cevabı bir değişkene (response) atıyoruz
      const response = await axios.post("http://localhost:8080/auth/login", form);
      
      console.log("Sunucu yanıtı:", response.data);

      // 🚀 2. Eğer backend'den token geldiyse Local Storage'a kaydediyoruz
      if (response.data.token) {
        localStorage.setItem("token", response.data.token);
        
        // Varsa rol bilgisini de kaydedebilirsin (Profil sayfası yetkileri için iyi olur)
        if (response.data.role) {
          localStorage.setItem("role", response.data.role);
        }

        setMessage("Login successful!");
        
        // 🚀 3. Token kaydedildikten sonra yönlendirme yapıyoruz
        setTimeout(() => {
          navigate("/home");
        }, 1000);
      } else {
        setMessage("Token bulunamadı. Backend yanıtını kontrol edin.");
      }

    } catch (error) {
      // 🚀 4. Obje hatasını stringe çevirerek React'in çökmesini engelliyoruz
      const errorMsg = error.response?.data?.message || error.response?.data || "Login failed.";
      setMessage(String(errorMsg));
    }
  };

  return (
    <div className="login-container">
      <img src={logo} alt="Logo" className="login-logo" />
      <h2 className="login-title">Login</h2>

      <form onSubmit={handleSubmit} className="login-form">
        <input className="login-input" type="email" name="email" placeholder="Email" value={form.email} onChange={handleChange} required />
        <input className="login-input" type="password" name="password" placeholder="Password" value={form.password} onChange={handleChange} required />

        <button type="submit" className="login-button">Login</button>
      </form>

      {message && <p className="login-message">{message}</p>}
    </div>
  );
};

export default Login;