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

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Frontend | React, React Router |
| Backend | Spring Boot, Java 17 |
| Database | MongoDB |
| AI | Google Gemini API |
| Email | JavaMailSender, Gmail SMTP |
| Auth | JWT |

## Project Structure

**Backend** (`backend/mentalhealthchatbot/src/main/java/`)
- `Controller/` — AuthController, ChatController, MoodController
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
- MongoDB running locally on port 27017
- Google Gemini API key
- Gmail account with App Password enabled

### Environment Variables
Set these in your system environment variables:
GEMINI_API_KEY=your_gemini_api_key
MAIL_PASSWORD=your_gmail_app_password

### Backend
```bash
cd backend/mentalhealthchatbot
./mvnw spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
npm start
```

## Privacy
Sera stores conversation history per session so you can revisit past chats. Only mood labels and timestamps are analyzed for weekly reports — never the actual conversation content.
## Disclaimer
Sera is an AI companion and is not a substitute for professional mental health care. If you are in crisis, please contact a mental health professional.
