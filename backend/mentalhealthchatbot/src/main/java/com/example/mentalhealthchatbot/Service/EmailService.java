package com.example.mentalhealthchatbot.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${SENDGRID_API_KEY}")
    private String sendGridApiKey;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    // ===== PUBLIC METHODS =====

    public void sendPasswordResetEmail(String toEmail, String token) {

        String resetLink = buildResetLink(token);

        String subject = "Sera - Reset Your Password";

        String body = "Hi,\n\n"
                + "Click the link below to reset your password:\n\n"
                + resetLink + "\n\n"
                + "This link expires in 1 hour.\n\n"
                + "If you didn't request this, ignore this email.";

        sendEmail(toEmail, subject, body);
    }

    public void sendWeeklyReport(String toEmail, String name, String emailBody) {

        String subject = "Your weekly check-in with Sera 🌿";

        String body = emailBody + "\n\n— Sera 💙";

        sendEmail(toEmail, subject, body);
    }

    // ===== CORE EMAIL SENDER =====

    private void sendEmail(String toEmail, String subject, String body) {

        validateConfig();

        Email from = new Email("seramentalwellness@gmail.com"); // must be verified in SendGrid
        Email to = new Email(toEmail);

        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);

        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            // IMPORTANT: always log response
            System.out.println("SendGrid Status: " + response.getStatusCode());
            System.out.println("SendGrid Body: " + response.getBody());

            if (response.getStatusCode() >= 400) {
                throw new RuntimeException("SendGrid failed: " + response.getBody());
            }

        } catch (IOException ex) {
            throw new RuntimeException("Email sending failed", ex);
        }
    }

    // ===== HELPERS =====

    private void validateConfig() {
        if (sendGridApiKey == null || sendGridApiKey.isBlank()) {
            throw new RuntimeException("Missing SENDGRID_API_KEY in environment");
        }

        if (frontendUrl == null || frontendUrl.isBlank()) {
            throw new RuntimeException("Missing app.frontend.url in environment");
        }
    }

    private String buildResetLink(String token) {

        String base = frontendUrl.endsWith("/")
                ? frontendUrl.substring(0, frontendUrl.length() - 1)
                : frontendUrl;

        return base + "/reset-password?token=" + token;
    }
}
