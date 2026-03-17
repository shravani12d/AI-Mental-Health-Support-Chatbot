package com.example.mentalhealthchatbot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mentalhealthchatbot.model.ChatMessage;
import com.example.mentalhealthchatbot.repository.ChatRepository;

import java.time.LocalDateTime;

@Service
public class ChatService {

    @Autowired
    private GroqService groqService;

    @Autowired
    private ChatRepository chatRepository;

    public String generateReply(String message){

        // Get AI response from OpenAI
        String reply = groqService.getAIResponse(message);

        // Save chat in MongoDB
        ChatMessage chat = new ChatMessage();
        chat.setUserMessage(message);
        chat.setBotReply(reply);
        chat.setTimestamp(LocalDateTime.now().toString());

        chatRepository.save(chat);

        return reply;
    }
}