package com.example.mentalhealthchatbot.repository;

import com.example.mentalhealthchatbot.model.MoodEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface MoodEntryRepository extends MongoRepository<MoodEntry, String> {
    List<MoodEntry> findByUserIdAndTimestampAfter(String userId, LocalDateTime after);
}