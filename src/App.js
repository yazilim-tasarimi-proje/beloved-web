import { Routes, Route } from "react-router-dom";


import Giris from "./home/Giris";
import Login from "./kayit-giris/Login";
import Register from "./kayit-giris/Register";
import Home from "./home/Home";
import Address from "./pages/Address";
import Favorite from "./pages/Favorite";
import Product from "./pages/Product";
import Cart from "./pages/Cart";
import User from "./pages/User";
import Order from "./pages/Order";
import Review from "./pages/Review";
import ProductDetail from "./pages/ProductDetail";

function App() {
  return (
    
      <Routes>
        <Route path="/" element={<Giris />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/home" element={<Home />} />
        <Route path="/address" element={<Address />} />
        <Route path="/favorites" element={<Favorite />} />
        <Route path="/products" element={<Product/>}/>
        <Route path="/cart" element={<Cart/>}/>
        <Route path="/user" element={<User/>}/>
        <Route path="/order" element={<Order/>}/>
        <Route path="/review" element={<Review/>}/>
        <Route path="/productdetail" element={<ProductDetail/>}/>

      </Routes>
    
  );
}

export default App;
