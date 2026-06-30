import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import "./Auth.css";
import { API_URL } from "./config";
function Register() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleRegister = async () => {
    if (!name || !email || !password) {
      setError("Please fill in all fields");
      return;
    }
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailRegex.test(email)) {
    setError("Please enter a valid email address");
    return;
}

    if (password.length < 6) {
      setError("Password must be at least 6 characters");
      return;
    }

    setLoading(true);
    setError("");

    try {
      const response = await fetch(`${API_URL}/api/auth/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, email, password })
      });

      const data = await response.json();

      if (!response.ok) {
        setError(data || "Registration failed");
        setLoading(false);
        return;
      }

      localStorage.setItem("token", data.token);
      localStorage.setItem("name", data.name);
      localStorage.setItem("email", data.email);

      navigate("/chat");
    } catch (err) {
      setError("Cannot connect to server. Please try again.");
    }

    setLoading(false);
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") handleRegister();
  };

  return (
    <div className="auth-wrapper">
      <div className="auth-left">
        <div className="auth-brand">
          <div className="auth-leaf">
            <svg viewBox="0 0 100 120" width="52" height="62">
              <defs>
                <linearGradient id="leafGrad3" x1="0%" y1="0%" x2="100%" y2="100%">
                  <stop offset="0%" stopColor="#8fe840"/>
                  <stop offset="40%" stopColor="#5dc93a"/>
                  <stop offset="100%" stopColor="#3a9a2a"/>
                </linearGradient>
              </defs>
              <path d="M50 5 C60 10, 80 15, 85 30 C90 45, 82 58, 75 65 C65 75, 55 80, 50 85 C45 80, 35 75, 25 65 C18 58, 10 45, 15 30 C20 15, 40 10, 50 5 Z" fill="url(#leafGrad3)" stroke="#3a9a2a" strokeWidth="1"/>
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
        <p className="auth-desc">Join thousands who have found support, clarity and calm with Sera.</p>

        <div className="auth-features">
          <div className="auth-feature">🌿 Empathetic AI conversations</div>
          <div className="auth-feature">🔒 Private and secure</div>
          <div className="auth-feature">💙 Available anytime</div>
          <div className="auth-feature">🧘 Guided wellness support</div>
        </div>
      </div>

      <div className="auth-right">
        <div className="auth-card">
          <h2 className="auth-title">Create account</h2>
          <p className="auth-subtitle">Start your wellness journey with Sera</p>

          {error && <div className="auth-error">{error}</div>}

          <div className="auth-field">
            <label>Full Name</label>
            <input
              type="text"
              placeholder="Your name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              onKeyDown={handleKeyDown}
            />
          </div>

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
              placeholder="Minimum 6 characters"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              onKeyDown={handleKeyDown}
            />
          </div>

          <button
            className="auth-btn"
            onClick={handleRegister}
            disabled={loading}
          >
            {loading ? "Creating account..." : "Create Account"}
          </button>

          <p className="auth-switch">
            Already have an account?{" "}
            <Link to="/login">Sign in</Link>
          </p>
        </div>
      </div>
    </div>
  );
}

export default Register;
