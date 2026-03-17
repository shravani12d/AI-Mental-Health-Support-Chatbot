package com.example.mentalhealthchatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class MentalhealthchatbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(MentalhealthchatbotApplication.class, args);
    }
}