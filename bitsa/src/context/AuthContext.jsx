import React, { createContext, useState, useEffect } from "react";
import axios from "axios";

export const AuthContext = createContext();

export const useAuth = () => {
  const context = React.useContext(AuthContext);
  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(localStorage.getItem("token"));

  useEffect(() => {
    axios.defaults.baseURL = import.meta.env.VITE_API_BASE_URL;
    if (token) {
      axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
      axios.get("/api/auth/profile")
        .then(response => setUser(response.data))
        .catch(() => {
          setToken(null);
          localStorage.removeItem("token");
          delete axios.defaults.headers.common["Authorization"];
        });
    }
  }, [token]);

  async function login(email, password) {
    try {
      const response = await axios.post("/api/auth/login", { email, password });
      const { token, id, name, email: userEmail, role } = response.data;
      localStorage.setItem("token", token);
      setToken(token);
      setUser({ id, name, email: userEmail, role });
      return true;
    } catch (error) {
      console.error("Login failed:", error);
      return false;
    }
  }

  async function register(userData) {
    try {
      const response = await axios.post("/api/auth/register", userData);
      // Optionally log in the user after successful registration
      // await login(userData.email, userData.password);
      return response.data;
    } catch (error) {
      console.error("Registration failed:", error);
      return false;
    }
  }

  function logout() {
    setToken(null);
    setUser(null);
    localStorage.removeItem("token");
    delete axios.defaults.headers.common["Authorization"];
  }

  return (
    <AuthContext.Provider value={{ user, token, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
