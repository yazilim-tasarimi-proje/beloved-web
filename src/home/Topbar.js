import React from "react";
import "../css/Home.css";

const Topbar = () => {
    return (
        <div className="topbar">
            <div className="topbar-content">
                <span className="topbar-item">📞 +90 555 123 45 67</span>
                <span className="topbar-item">🚚 500 TL Üzeri Ücretsiz Kargo!</span>
            </div>
        </div>
    );
};

export default Topbar;