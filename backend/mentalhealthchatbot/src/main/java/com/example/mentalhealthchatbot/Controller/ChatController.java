package com.example.mentalhealthchatbot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.mentalhealthchatbot.model.ChatMessage;
import com.example.mentalhealthchatbot.repository.ChatRepository;
import com.example.mentalhealthchatbot.Service.ChatService;

import java.util.Date;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatRepository chatRepository;

   @PostMapping
public String chat(@RequestBody String message){
    return chatService.generateReply(message);
}
}