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
      await axios.post("http://localhost:8080/auth/login", form);
      setMessage("Login successful!");
      navigate("/home");     // 🔥 Home1'e yönlendirme
    } catch (error) {
      setMessage(error.response?.data || "Login failed.");
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
