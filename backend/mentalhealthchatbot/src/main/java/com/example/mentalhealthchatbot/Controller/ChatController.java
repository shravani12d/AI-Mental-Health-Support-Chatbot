package com.example.mentalhealthchatbot.Controller;

import com.example.mentalhealthchatbot.model.ChatSession;
import com.example.mentalhealthchatbot.repository.ChatSessionRepository;
import com.example.mentalhealthchatbot.Service.ChatService;
import com.example.mentalhealthchatbot.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatSessionRepository chatSessionRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private String extractEmail(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return jwtUtil.extractEmail(token);
    }

    // Send message - saves to existing or new session
   @PostMapping("/chat")
public String chat(
        @RequestBody String message,
        @RequestHeader(value = "Authorization", required = false) String authHeader,
        @RequestParam(value = "sessionId", required = false) String sessionId) {

    String email = null;
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        email = extractEmail(authHeader);
    }

    // Pass history context to service
    String reply = chatService.generateReply(message, sessionId, email);

    if (email != null) {
        ChatSession session;

        if (sessionId != null) {
            session = chatSessionRepository
                    .findByIdAndUserEmail(sessionId, email)
                    .orElse(new ChatSession(email));
        } else {
            session = new ChatSession(email);
        }

        if (session.getMessages().isEmpty()) {
            String title = message.length() > 40
                    ? message.substring(0, 40) + "..."
                    : message;
            session.setTitle(title);
        }

        session.getMessages().add(new ChatSession.MessagePair(message, reply));
        session.setUpdatedAt(LocalDateTime.now());
        ChatSession saved = chatSessionRepository.save(session);

        return reply + "|||" + saved.getId();
    }

    return reply;
}

    // Get all sessions for sidebar
    @GetMapping("/sessions")
    public List<ChatSession> getSessions(
            @RequestHeader("Authorization") String authHeader) {
        String email = extractEmail(authHeader);
        return chatSessionRepository.findByUserEmailOrderByUpdatedAtDesc(email);
    }

    // Get one specific session
    @GetMapping("/sessions/{sessionId}")
    public ChatSession getSession(
            @PathVariable String sessionId,
            @RequestHeader("Authorization") String authHeader) {
        String email = extractEmail(authHeader);
        return chatSessionRepository
                .findByIdAndUserEmail(sessionId, email)
                .orElse(null);
    }

    // Delete one session
    @DeleteMapping("/sessions/{sessionId}")
    public String deleteSession(
            @PathVariable String sessionId,
            @RequestHeader("Authorization") String authHeader) {
        String email = extractEmail(authHeader);
        chatSessionRepository.findByIdAndUserEmail(sessionId, email)
                .ifPresent(chatSessionRepository::delete);
        return "Session deleted!";
    }

    // Delete all sessions
    @DeleteMapping("/sessions")
    public String deleteAllSessions(
            @RequestHeader("Authorization") String authHeader) {
        String email = extractEmail(authHeader);
        chatSessionRepository.deleteByUserEmail(email);
        return "All sessions deleted!";
    }
}