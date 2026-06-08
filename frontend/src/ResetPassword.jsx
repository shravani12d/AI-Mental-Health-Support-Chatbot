import React, { useState } from "react";
import { Link, useNavigate, useSearchParams } from "react-router-dom";

function ResetPassword() {
  const [searchParams] = useSearchParams();
  const token = searchParams.get("token");
  const navigate = useNavigate();

  const [password, setPassword] = useState("");
  const [confirm, setConfirm] = useState("");
  const [status, setStatus] = useState(null);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (password !== confirm) { setStatus("error"); setMessage("Passwords do not match."); return; }
    if (password.length < 6) { setStatus("error"); setMessage("Password must be at least 6 characters."); return; }

    setLoading(true);
    setStatus(null);
    try {
      const res = await fetch("http://localhost:8080/api/auth/reset-password", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ token, password }),
      });
      const text = await res.text();
      if (res.ok) {
        setStatus("success");
        setMessage(text);
        setTimeout(() => navigate("/login"), 2500);
      } else {
        setStatus("error");
        setMessage(text);
      }
    } catch (err) {
      setStatus("error");
      setMessage("Something went wrong. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  if (!token) {
    return (
      <div className="auth-wrapper">
        <div className="auth-right" style={{ width: "100%" }}>
          <div className="auth-card">
            <h1 className="auth-title">Invalid link</h1>
            <p className="auth-subtitle">This reset link is missing or malformed.</p>
            <div className="auth-switch"><Link to="/forgot-password">Request a new reset link</Link></div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="auth-wrapper">
      <div className="auth-left">
        <div className="auth-brand">
          <div className="auth-leaf">
            <svg viewBox="0 0 100 120" width="42" height="50">
              <path d="M50 5 C55 5, 63 10, 70 18 C76 25, 80 30, 82 38 C85 48, 83 57, 79 64 C75 71, 68 77, 62 82 C57 86, 53 89, 50 90 C47 89, 43 86, 38 82 C32 77, 25 71, 21 64 C17 57, 15 48, 18 38 C20 30, 24 25, 30 18 C37 10, 45 5, 50 5 Z" fill="url(#lg3)" stroke="#3a9a2a" strokeWidth="1"/>
              <path d="M50 8 C50 25, 50 55, 50 88" fill="none" stroke="#2d7a1e" strokeWidth="2" strokeLinecap="round"/>
              <path d="M49 22 C44 25, 37 27, 28 29" fill="none" stroke="#2d7a1e" strokeWidth="1.1" strokeLinecap="round"/>
              <path d="M49 42 C43 45, 36 47, 25 48" fill="none" stroke="#2d7a1e" strokeWidth="1.1" strokeLinecap="round"/>
              <path d="M51 22 C56 25, 63 27, 72 29" fill="none" stroke="#2d7a1e" strokeWidth="1.1" strokeLinecap="round"/>
              <path d="M51 42 C57 45, 64 47, 75 48" fill="none" stroke="#2d7a1e" strokeWidth="1.1" strokeLinecap="round"/>
              <defs>
                <linearGradient id="lg3" x1="0%" y1="0%" x2="100%" y2="100%">
                  <stop offset="0%" stopColor="#8fe840"/>
                  <stop offset="100%" stopColor="#3a9a2a"/>
                </linearGradient>
              </defs>
            </svg>
          </div>
          <p className="auth-logo-text">Sera</p>
        </div>
        <p className="auth-tagline">Your personal wellness companion</p>
        <p className="auth-desc">Choose a strong password. You'll be redirected to login automatically after resetting.</p>
        <div className="auth-features">
          <div className="auth-feature">🔐 Minimum 6 characters</div>
          <div className="auth-feature">✅ Old password will be replaced</div>
          <div className="auth-feature">↩️ Redirects to login automatically</div>
        </div>
      </div>

      <div className="auth-right">
        <div className="auth-card">
          <h1 className="auth-title">Reset password</h1>
          <p className="auth-subtitle">Enter your new password below.</p>

          {status === "error" && <div className="auth-error">{message}</div>}

          {status === "success" ? (
            <div style={{ background: "#f0faf4", border: "1.5px solid rgba(90,138,114,0.3)", color: "#3a7a58", padding: "16px 18px", borderRadius: "12px", fontSize: "15px", lineHeight: "1.6", marginBottom: "24px" }}>
              ✅ {message}. Redirecting to login...
            </div>
          ) : (
            <form onSubmit={handleSubmit}>
              <div className="auth-field">
                <label>New Password</label>
                <input type="password" placeholder="Enter your new password" value={password} onChange={(e) => setPassword(e.target.value)} required />
              </div>
              <div className="auth-field">
                <label>Confirm Password</label>
                <input type="password" placeholder="Confirm your new password" value={confirm} onChange={(e) => setConfirm(e.target.value)} required />
              </div>
              <button className="auth-btn" type="submit" disabled={loading}>
                {loading ? "Resetting..." : "Reset password"}
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

export default ResetPassword;