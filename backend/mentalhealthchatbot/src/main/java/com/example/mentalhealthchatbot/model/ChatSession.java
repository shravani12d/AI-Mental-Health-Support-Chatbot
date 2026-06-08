package com.example.mentalhealthchatbot.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "chat_sessions")
public class ChatSession {

    @Id
    private String id;

    private String userEmail;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<MessagePair> messages = new ArrayList<>();

    public ChatSession() {}

    public ChatSession(String userEmail) {
        this.userEmail = userEmail;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.title = "New Conversation";
    }

    // Inner class for each message pair
    public static class MessagePair {
        private String userMessage;
        private String botReply;
        private String timestamp;

        public MessagePair() {}

        public MessagePair(String userMessage, String botReply) {
            this.userMessage = userMessage;
            this.botReply = botReply;
            this.timestamp = LocalDateTime.now().toString();
        }

        public String getUserMessage() { return userMessage; }
        public void setUserMessage(String userMessage) { this.userMessage = userMessage; }
        public String getBotReply() { return botReply; }
        public void setBotReply(String botReply) { this.botReply = botReply; }
        public String getTimestamp() { return timestamp; }
        public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public List<MessagePair> getMessages() { return messages; }
    public void setMessages(List<MessagePair> messages) { this.messages = messages; }
}
