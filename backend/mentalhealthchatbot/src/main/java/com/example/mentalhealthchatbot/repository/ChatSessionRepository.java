package com.example.mentalhealthchatbot.repository;

import com.example.mentalhealthchatbot.model.ChatSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface ChatSessionRepository extends MongoRepository<ChatSession, String> {
    
    // Get all sessions for a user (newest first)
    List<ChatSession> findByUserEmailOrderByUpdatedAtDesc(String userEmail);
    
    // Get one specific session
    Optional<ChatSession> findByIdAndUserEmail(String id, String userEmail);
    
    // Delete all sessions for a user
    void deleteByUserEmail(String userEmail);
}