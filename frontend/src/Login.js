import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import "./Auth.css";

function Login({ setToken }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleLogin = async () => {
    if (!email || !password) {
      setError("Please fill in all fields");
      return;
    }
 
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

if (!emailRegex.test(email)) {
  setError("Please enter a valid email address");
  return;
}
    setLoading(true);
    setError("");

    try {
      const response = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
      });

      const data = await response.json();

      if (!response.ok) {
        setError(data || "Login failed");
        setLoading(false);
        return;
      }

      localStorage.setItem("token", data.token);
      localStorage.setItem("name", data.name);
      localStorage.setItem("email", data.email);
      setToken(data.token);
      navigate("/chat");

      navigate("/chat");
    } catch (err) {
      setError("Cannot connect to server. Please try again.");
    }

    setLoading(false);
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") handleLogin();
  };

  return (
    <div className="auth-wrapper">
      <div className="auth-left">
        <div className="auth-brand">
          <div className="auth-leaf">
            <svg viewBox="0 0 100 120" width="52" height="62">
              <defs>
                <linearGradient id="leafGrad2" x1="0%" y1="0%" x2="100%" y2="100%">
                  <stop offset="0%" stopColor="#8fe840"/>
                  <stop offset="40%" stopColor="#5dc93a"/>
                  <stop offset="100%" stopColor="#3a9a2a"/>
                </linearGradient>
              </defs>
              <path d="M50 5 C60 10, 80 15, 85 30 C90 45, 82 58, 75 65 C65 75, 55 80, 50 85 C45 80, 35 75, 25 65 C18 58, 10 45, 15 30 C20 15, 40 10, 50 5 Z" fill="url(#leafGrad2)" stroke="#3a9a2a" strokeWidth="1"/>
              <path d="M50 8 C50 25, 50 55, 50 83" fill="none" stroke="#2d7a1e" strokeWidth="2" strokeLinecap="round"/>
              <path d="M49 22 C44 25, 37 27, 28 29" fill="none" stroke="#2d7a1e" strokeWidth="1.1" strokeLinecap="round"/>
              <path d="M49 32 C43 35, 36 37, 26 39" fill="none" stroke="#2d7a1e" strokeWidth="1.1" strokeLinecap="round"/>
              <path d="M49 42 C43 45, 36 47, 25 48" fill="none" stroke="#2d7a1e" strokeWidth="1.1" strokeLinecap="round"/>
              <path d="M51 22 C56 25, 63 27, 72 29" fill="none" stroke="#2d7a1e" strokeWidth="1.1" strokeLinecap="round"/>
              <path d="M51 32 C57 35, 64 37, 74 39" fill="none" stroke="#2d7a1e" strokeWidth="1.1" strokeLinecap="round"/>
              <path d="M51 42 C57 45, 64 47, 75 48" fill="none" stroke="#2d7a1e" strokeWidth="1.1" strokeLinecap="round"/>
              <path d="M50 84 C49 88, 47 92, 44 96" fill="none" stroke="#2d7a1e" strokeWidth="2" strokeLinecap="round"/>
            </svg>
          </div>
          <h1 className="auth-logo-text">Sera</h1>
        </div>
        <p className="auth-tagline">Your personal wellness companion</p>
        <p className="auth-desc">A safe space to talk, reflect, and find support whenever you need it.</p>

        <div className="auth-features">
          <div className="auth-feature">🌿 Empathetic AI conversations</div>
          <div className="auth-feature">🔒 Private and secure</div>
          <div className="auth-feature">💙 Available anytime</div>
          <div className="auth-feature">🧘 Guided wellness support</div>
        </div>
      </div>

      <div className="auth-right">
        <div className="auth-card">
          <h2 className="auth-title">Welcome back</h2>
          <p className="auth-subtitle">Sign in to continue your wellness journey</p>

          {error && <div className="auth-error">{error}</div>}

          <div className="auth-field">
            <label>Email</label>
            <input
              type="email"
              placeholder="your@email.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              onKeyDown={handleKeyDown}
            />
          </div>

          <div className="auth-field">
            <label>Password</label>
            <input
              type="password"
              placeholder="Enter your password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              onKeyDown={handleKeyDown}
            />
          </div>

          <div style={{ textAlign: "right", marginBottom: "20px" }}>
          <Link to="/forgot-password" style={{ fontSize: "13px", color: "#7a9e8a", textDecoration: "none" }}>
           Forgot password?
          </Link>
          </div>

          <button
            className="auth-btn"
            onClick={handleLogin}
            disabled={loading}
          >
            {loading ? "Signing in..." : "Sign In"}
          </button>

          <p className="auth-switch">
            Don't have an account?{" "}
            <Link to="/register">Create one</Link>
          </p>
        </div>
      </div>
    </div>
  );
}

export default Login;
