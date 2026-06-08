package com.example.mentalhealthchatbot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mentalhealthchatbot.model.ChatSession;
import com.example.mentalhealthchatbot.repository.ChatSessionRepository;

import java.util.*;

@Service
public class ChatService {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private ChatSessionRepository chatSessionRepository;

    public String generateReply(String message, String sessionId, String email) {

        // Build history from session
        List<Map<String, String>> history = new ArrayList<>();

        if (sessionId != null && email != null) {
            Optional<ChatSession> sessionOpt = chatSessionRepository
                .findByIdAndUserEmail(sessionId, email);

            if (sessionOpt.isPresent()) {
                ChatSession session = sessionOpt.get();

                // Add last 10 message pairs to keep context
                List<ChatSession.MessagePair> messages = session.getMessages();
                int start = Math.max(0, messages.size() - 10);

                for (int i = start; i < messages.size(); i++) {
                    ChatSession.MessagePair pair = messages.get(i);

                    // Add user message to history
                    Map<String, String> userMsg = new HashMap<>();
                    userMsg.put("role", "user");
                    userMsg.put("content", pair.getUserMessage());
                    history.add(userMsg);

                    // Add bot reply to history
                    Map<String, String> botMsg = new HashMap<>();
                    botMsg.put("role", "assistant");
                    botMsg.put("content", pair.getBotReply());
                    history.add(botMsg);
                }
            }
        }

        // Pass history to Groq
        return geminiService.getAIResponse(message, history);
    }
}