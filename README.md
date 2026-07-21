# Sera - AI Mental Health Companion 🌿

Sera is an empathetic AI-powered mental wellness chatbot designed to provide emotional support, guidance, and weekly wellness insights to users dealing with everyday challenges.

## Features

- 💬 **Empathetic AI Conversations** — Powered by Google Gemini, Sera responds like a warm, compassionate wellness companion
- 🔐 **Secure Authentication** — JWT-based login, registration, and forgot/reset password via email
- 🧠 **Mood Tracking** — Users log their mood through sidebar buttons; data is stored privately in MongoDB
- 📧 **Weekly Wellness Email** — Every Sunday at 7am, Sera sends a personalized AI-generated email based on the user's mood patterns from the week
- 💌 **Re-engagement Emails** — Users who go silent for a week receive a gentle "we missed you" message
- 🆘 **Crisis Support** — Built-in helpline numbers (Tele-MANAS 14416, iCALL 9152987821) surface automatically in crisis situations
- 💾 **Conversation History** — Past chat sessions are saved and accessible from the sidebar
  

🔗 Live Demo https://ai-mental-health-support-chatbot.vercel.app

Note: This is hosted on a free-tier backend, so the first message may take 30-50 seconds to respond while the server wakes up.

Note: Emails may land in spam due to sender authentication limitations on the free-tier email setup — check your spam folder if you don't see the check-in email in your inbox.

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Frontend | React, React Router |
| Backend | Spring Boot, Java 17 |
| Database | MongoDB |
| AI | Google Gemini API |
| Email | SendGrid |
| Auth | JWT |

## Project Structure

**Backend** (`backend/mentalhealthchatbot/src/main/java/`)
- `Controller/` — AuthController, ChatController, MoodController, AdminController
- `Service/` — ChatService, EmailService, GeminiService, WeeklyReportService
- `model/` — User, ChatSession, ChatMessage, MoodEntry
- `repository/` — UserRepository, ChatSessionRepository, MoodEntryRepository
- `config/` — SecurityConfig

**Frontend** (`frontend/src/`)
- `App.js` — Main chat UI
- `Login.jsx`, `Register.jsx` — Authentication
- `ForgotPassword.jsx`, `ResetPassword.jsx` — Password reset flow

## Setup

### Prerequisites
- Java 17
- Node.js
- MongoDB Atlas account (cloud-hosted)
- Google Gemini API key
- SendGrid account with verified sender

### Environment Variables

Backend (set in Render dashboard):
MONGO_URI=your_mongodb_atlas_connection_string
JWT_SECRET=your_jwt_secret
GEMINI_API_KEY=your_gemini_api_key
SENDGRID_API_KEY=your_sendgrid_api_key
FRONTEND_URL=your_deployed_frontend_url
ADMIN_TRIGGER_SECRET=your_admin_trigger_secret

Frontend (set in Vercel dashboard):
REACT_APP_API_URL=your_deployed_backend_url


Deployment
This project is deployed using:
- Frontend: Vercel
- Backend: Render (Docker)
- Database: MongoDB Atlas
- Note: Weekly email delivery is triggered via an external scheduler (cron-job.org) calling a secured admin endpoint, since Render's free tier does not reliably run internal background schedulers unattended.
- AdminController exposes a secured endpoint (/api/admin/trigger-weekly-report) used by the external scheduler to invoke the weekly email logic.

## Privacy
Sera stores conversation history per session so you can revisit past chats. Only mood labels and timestamps are analyzed for weekly reports — never the actual conversation content.
## Disclaimer
Sera is an AI companion and is not a substitute for professional mental health care. If you are in crisis, please contact a mental health professional.
