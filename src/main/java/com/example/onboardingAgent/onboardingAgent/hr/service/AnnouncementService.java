package com.example.onboardingAgent.onboardingAgent.hr.service;

import com.example.onboardingAgent.onboardingAgent.ai.PromptTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final GeminiApiService gemini;
    private final PromptTemplateService prompts;

    public String format(String payload) {
        return gemini.generateText(prompts.formatHrMessage(payload));
    }
}
