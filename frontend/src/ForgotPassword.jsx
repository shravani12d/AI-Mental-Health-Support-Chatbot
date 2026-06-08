import React, { useState } from "react";
import { Link } from "react-router-dom";

function ForgotPassword() {
  const [email, setEmail] = useState("");
  const [status, setStatus] = useState(null);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setStatus(null);
    try {
      const res = await fetch("http://localhost:8080/api/auth/forgot-password", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email }),
      });
      const text = await res.text();
      setStatus("success");
      setMessage(text);
    } catch (err) {
      setStatus("error");
      setMessage("Something went wrong. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-wrapper">
      <div className="auth-left">
        <div className="auth-brand">
          <div className="auth-leaf">
            <svg viewBox="0 0 100 120" width="42" height="50">
              <path d="M50 5 C55 5, 63 10, 70 18 C76 25, 80 30, 82 38 C85 48, 83 57, 79 64 C75 71, 68 77, 62 82 C57 86, 53 89, 50 90 C47 89, 43 86, 38 82 C32 77, 25 71, 21 64 C17 57, 15 48, 18 38 C20 30, 24 25, 30 18 C37 10, 45 5, 50 5 Z" fill="url(#lg2)" stroke="#3a9a2a" strokeWidth="1"/>
              <path d="M50 8 C50 25, 50 55, 50 88" fill="none" stroke="#2d7a1e" strokeWidth="2" strokeLinecap="round"/>
              <path d="M49 22 C44 25, 37 27, 28 29" fill="none" stroke="#2d7a1e" strokeWidth="1.1" strokeLinecap="round"/>
              <path d="M49 42 C43 45, 36 47, 25 48" fill="none" stroke="#2d7a1e" strokeWidth="1.1" strokeLinecap="round"/>
              <path d="M51 22 C56 25, 63 27, 72 29" fill="none" stroke="#2d7a1e" strokeWidth="1.1" strokeLinecap="round"/>
              <path d="M51 42 C57 45, 64 47, 75 48" fill="none" stroke="#2d7a1e" strokeWidth="1.1" strokeLinecap="round"/>
              <defs>
                <linearGradient id="lg2" x1="0%" y1="0%" x2="100%" y2="100%">
                  <stop offset="0%" stopColor="#8fe840"/>
                  <stop offset="100%" stopColor="#3a9a2a"/>
                </linearGradient>
              </defs>
            </svg>
          </div>
          <p className="auth-logo-text">Sera</p>
        </div>
        <p className="auth-tagline">Your personal wellness companion</p>
        <p className="auth-desc">No worries — enter your email and we'll send you a secure link to reset your password.</p>
        <div className="auth-features">
          <div className="auth-feature">🔒 Secure reset link</div>
          <div className="auth-feature">⏱️ Link expires in 1 hour</div>
          <div className="auth-feature">📧 Check your spam folder too</div>
        </div>
      </div>

      <div className="auth-right">
        <div className="auth-card">
          <h1 className="auth-title">Forgot password?</h1>
          <p className="auth-subtitle">We'll send a reset link to your email.</p>

          {status === "error" && <div className="auth-error">{message}</div>}

          {status === "success" ? (
            <div style={{ background: "#f0faf4", border: "1.5px solid rgba(90,138,114,0.3)", color: "#3a7a58", padding: "16px 18px", borderRadius: "12px", fontSize: "15px", lineHeight: "1.6", marginBottom: "24px" }}>
              ✅ {message}
            </div>
          ) : (
            <form onSubmit={handleSubmit}>
              <div className="auth-field">
                <label>Email</label>
                <input type="email" placeholder="you@email.com" value={email} onChange={(e) => setEmail(e.target.value)} required />
              </div>
              <button className="auth-btn" type="submit" disabled={loading}>
                {loading ? "Sending..." : "Send reset link"}
              </button>
            </form>
          )}

          <div className="auth-switch">
            <Link to="/login">← Back to sign in</Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ForgotPassword;