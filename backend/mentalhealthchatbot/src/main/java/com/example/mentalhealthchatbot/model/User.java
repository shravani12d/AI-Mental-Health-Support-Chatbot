package com.example.mentalhealthchatbot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.time.LocalDateTime;
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String name;
    private String resetToken;
    private String resetTokenExpiry;
   
    @Indexed(unique = true)
    private String email;

    private String password;
    private LocalDateTime lastMissedYouEmailSent;
    public User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public String getResetToken() { return resetToken; }
    public String getResetTokenExpiry() { return resetTokenExpiry; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }
    public void setResetTokenExpiry(String resetTokenExpiry) { this.resetTokenExpiry = resetTokenExpiry; }
    public LocalDateTime getLastMissedYouEmailSent() { return lastMissedYouEmailSent;}
    public void setLastMissedYouEmailSent(LocalDateTime lastMissedYouEmailSent) {this.lastMissedYouEmailSent = lastMissedYouEmailSent;}
}
