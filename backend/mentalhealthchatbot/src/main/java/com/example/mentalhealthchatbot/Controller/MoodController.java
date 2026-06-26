package com.example.mentalhealthchatbot.Controller;

import com.example.mentalhealthchatbot.model.MoodEntry;
import com.example.mentalhealthchatbot.repository.MoodEntryRepository;
import com.example.mentalhealthchatbot.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.mentalhealthchatbot.model.User;
import com.example.mentalhealthchatbot.repository.UserRepository;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/mood")
@CrossOrigin(origins = "http://localhost:3000")
public class MoodController {

    @Autowired
    private MoodEntryRepository moodEntryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/log")
    public ResponseEntity<?> logMood(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, String> request) {

        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        String mood = request.get("mood");
        if (mood == null || mood.isBlank()) {
            return ResponseEntity.badRequest().body("Mood is required");
        }

       
        String cleanMood = mood.replaceAll("[^a-zA-Z ]", "").trim();
        if (cleanMood.startsWith("Im feeling ")) {
            cleanMood = cleanMood.substring("Im feeling ".length()).trim();
        }

       MoodEntry entry = new MoodEntry(email, cleanMood);
       moodEntryRepository.save(entry);

       Optional<User> optionalUser = userRepository.findByEmail(email);

      if (optionalUser.isPresent()) {
       User user = optionalUser.get();
       user.setLastMissedYouEmailSent(null);
       userRepository.save(user);
        }

return ResponseEntity.ok("Mood logged");
    }
}
