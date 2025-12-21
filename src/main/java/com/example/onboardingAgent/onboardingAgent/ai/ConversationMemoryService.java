package com.example.onboardingAgent.onboardingAgent.ai;


import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ConversationMemoryService {

    private final Map<String, List<String>> memory = new HashMap<>();

    public void add(String userId, String message) {
        memory.computeIfAbsent(userId, k -> new ArrayList<>()).add(message);
    }

    public String context(String userId) {
        return String.join("\n", memory.getOrDefault(userId, List.of()));
    }

    public void clear(String userId) {
        memory.remove(userId);
    }
}
