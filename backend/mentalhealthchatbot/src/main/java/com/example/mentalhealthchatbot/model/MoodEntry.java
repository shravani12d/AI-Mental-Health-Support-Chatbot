package com.example.mentalhealthchatbot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "mood_entries")
public class MoodEntry {

    @Id
    private String id;
    private String userId;
    private String mood;
    private LocalDateTime timestamp;

    public MoodEntry() {}

    public MoodEntry(String userId, String mood) {
        this.userId = userId;
        this.mood = mood;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getMood() { return mood; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setId(String id) { this.id = id; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setMood(String mood) { this.mood = mood; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}