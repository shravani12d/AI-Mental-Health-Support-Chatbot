package com.example.mentalhealthchatbot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.mentalhealthchatbot.model.ChatMessage;

public interface ChatRepository extends MongoRepository<ChatMessage, String> {
}