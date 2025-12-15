package com.example.onboardingAgent.onboardingAgent.hr.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeminiApiService {

    private final Client geminiClient;

    public String generateText(String prompt) {

        GenerateContentResponse response =
                geminiClient.models.generateContent(
                        "gemini-2.5-flash",
                        prompt,
                        GenerateContentConfig.builder().build()
                );

        return response.text();
    }
}
