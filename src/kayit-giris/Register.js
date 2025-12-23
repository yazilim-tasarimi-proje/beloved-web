import React, { useState } from "react";
import axios from "axios";
import "../css/Register.css";
import logo from "../assets/1.png";
import { useNavigate } from "react-router-dom";

const Register = () => {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
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
      await axios.post("http://localhost:8080/auth/register", form);
      setMessage("Registered successfully!");

      setTimeout(() => {
        navigate("/login");  // 🔥 Yönlendirme
      }, 700);
    } catch (error) {
      setMessage(error.response?.data || "Registration failed.");
    }
  };

  return (
    <div className="register-container">
      <img src={logo} alt="Logo" className="register-logo" />
      <h2 className="register-title">Register</h2>

      <form onSubmit={handleSubmit} className="register-form">
        <input className="register-input" type="text" name="firstName" placeholder="First Name" value={form.firstName} onChange={handleChange} required />
        <input className="register-input" type="text" name="lastName" placeholder="Last Name" value={form.lastName} onChange={handleChange} required />
        <input className="register-input" type="email" name="email" placeholder="Email" value={form.email} onChange={handleChange} required />
        <input className="register-input" type="password" name="password" placeholder="Password" value={form.password} onChange={handleChange} required />

        <button type="submit" className="register-button">Register</button>
      </form>

      {message && <p className="register-message">{message}</p>}
    </div>
  );
};

export default Register;
