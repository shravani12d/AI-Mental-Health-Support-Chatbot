package com.example.mentalhealthchatbot.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public void sendPasswordResetEmail(String toEmail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Sera - Reset Your Password");
        message.setText("Hi,\n\nClick the link below to reset your password:\n\n"
            + frontendUrl + "/reset-password?token=" + token
            + "\n\nThis link expires in 1 hour.\n\nIf you didn't request this, ignore this email.");
        mailSender.send(message);
    }
    public void sendWeeklyReport(String toEmail, String name, String emailBody) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject("Your weekly check-in with Sera 🌿");
    message.setText(emailBody + "\n\n— Sera 💙\n\nThis is your automated weekly wellness check-in from Sera.");
    mailSender.send(message);
}
}