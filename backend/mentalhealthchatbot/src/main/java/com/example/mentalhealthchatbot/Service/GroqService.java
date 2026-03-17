package com.example.mentalhealthchatbot.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    public String getAIResponse(String message) {
        try {
            String url = "https://api.groq.com/openai/v1/chat/completions";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            // System message - defines bot personality
            Map<String, Object> systemMsg = new HashMap<>();
            systemMsg.put("role", "system");
            systemMsg.put("content",
    "You are Sera, a warm and empathetic mental health support companion, like a knowledgeable friend. " +
    "Give detailed, thoughtful and conversational responses like ChatGPT would. " +
    "Be warm, natural and engaging - not robotic or too short. " +
    "Use simple paragraphs to explain things clearly. " +
    "Do not use markdown symbols like ** or bullet points. " +
    "Be kind, gentle and non-judgmental. " +
    "Ask follow up questions to understand the person better. " +
    "If someone seems in crisis, gently suggest professional help."
);

            // User message
            Map<String, Object> userMsg = new HashMap<>();
            userMsg.put("role", "user");
            userMsg.put("content", message);

            List<Map<String, Object>> messages = new ArrayList<>();
            messages.add(systemMsg);
            messages.add(userMsg);

            Map<String, Object> body = new HashMap<>();
            body.put("model", "llama-3.3-70b-versatile");
            body.put("messages", messages);
            body.put("max_tokens", 150);
            body.put("temperature", 0.7);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            Map response = restTemplate.postForObject(url, request, Map.class);

            if (response == null) return "I'm here for you. Please try again.";

            List choices = (List) response.get("choices");
            if (choices == null || choices.isEmpty()) return "I'm here for you. Please try again.";

            Map choice = (Map) choices.get(0);
            Map msgResp = (Map) choice.get("message");

            return msgResp.get("content").toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "I'm having trouble connecting right now. Please try again in a moment.";
        }
    }
}


