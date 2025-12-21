package com.example.onboardingAgent.onboardingAgent.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class AiResponseParser {

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T parse(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("AI response parsing failed", e);
        }
    }
}
