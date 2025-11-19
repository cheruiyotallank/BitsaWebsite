import React, { useContext, useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ThemeContext } from "../context/ThemeContext";
import { useAuth } from "../context/AuthContext";

export default function Navbar() {
  const navigate = useNavigate();
  const { theme, toggle } = useContext(ThemeContext);
  const { user, logout } = useAuth();
  const [showNotifs, setShowNotifs] = useState(false);
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    const stored = JSON.parse(localStorage.getItem("bitsaNotifications")) || [
      { text: "New event added: AI Bootcamp 2025" },
      { text: "Your Hackathon certificate is ready!" },
    ];
    setNotifications(stored);
  }, []);

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <nav style={{ 
      backgroundColor: theme === "light" ? "#f8fafc" : "#0f172a", 
      color: theme === "light" ? "#0f172a" : "white", 
      padding: "1rem 2rem", 
      display: "flex", 
      justifyContent: "space-between", 
      alignItems: "center",
      position: "sticky",
      top: 0,
      zIndex: 10
    }}>
      <div style={{ fontWeight: "bold", fontSize: "1.2rem" }}>
        <Link to="/" style={{ color: theme === "light" ? "#0f172a" : "white", textDecoration: "none" }}>
          BITSA Club
        </Link>
      </div>

      <div style={{ display: "flex", gap: "1rem", alignItems: "center" }}>
        <Link to="/" style={{ color: theme === "light" ? "#0f172a" : "white", textDecoration: "none" }}>Home</Link>
        <Link to="/about" style={{ color: theme === "light" ? "#0f172a" : "white", textDecoration: "none" }}>About</Link>
        <Link to="/gallery" style={{ color: theme === "light" ? "#0f172a" : "white", textDecoration: "none" }}>Gallery</Link>
        <Link to="/events" style={{ color: theme === "light" ? "#0f172a" : "white", textDecoration: "none" }}>Events</Link>
        <Link to="/blogs" style={{ color: theme === "light" ? "#0f172a" : "white", textDecoration: "none" }}>Blogs</Link>

        {user ? (
          <>
            {user.role === "ADMIN" && <Link to="/dashboard/admin" style={{ color: theme === "light" ? "#0f172a" : "white", textDecoration: "none" }}>Admin</Link>}
            {user.role === "LECTURER" && <Link to="/dashboard/lecturer" style={{ color: theme === "light" ? "#0f172a" : "white", textDecoration: "none" }}>Lecturer</Link>}
            {user.role === "STUDENT" && <Link to="/dashboard/user" style={{ color: theme === "light" ? "#0f172a" : "white", textDecoration: "none" }}>Student</Link>}
            <button onClick={handleLogout} style={{ backgroundColor: "#dc2626", color: "white", border: "none", padding: "0.4rem 0.8rem", borderRadius: "5px", cursor: "pointer" }}>Logout</button>
          </>
        ) : (
          <>
            <Link to="/login" style={{ color: theme === "light" ? "#0f172a" : "white", textDecoration: "none" }}>Login</Link>
            <Link to="/register" style={{ color: theme === "light" ? "#0f172a" : "white", textDecoration: "none" }}>Register</Link>
          </>
        )}

        <div className="notif-section">
          <span className="bell" onClick={() => setShowNotifs(!showNotifs)} aria-label="Notifications">🔔</span>
          {showNotifs && (
            <div className="notif-dropdown">
              {notifications.map((n, i) => (
                <p key={i}>{n.text}</p>
              ))}
            </div>
          )}
        </div>

        <button
          onClick={toggle}
          style={{
            background: "transparent",
            border: "1px solid",
            borderColor: theme === "light" ? "#0f172a" : "white",
            color: theme === "light" ? "#0f172a" : "white",
            borderRadius: "5px",
            padding: "0.3rem 0.8rem",
            cursor: "pointer"
          }}
        >
          {theme === "light" ? "🌙" : "☀️"}
        </button>
      </div>
    </nav>
  );
}
