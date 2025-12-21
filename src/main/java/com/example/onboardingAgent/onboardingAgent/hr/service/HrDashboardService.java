package com.example.onboardingAgent.onboardingAgent.hr.service;

import com.example.onboardingAgent.onboardingAgent.hr.service.GeminiApiService;
import com.example.onboardingAgent.onboardingAgent.ai.PromptTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HrDashboardService {

    private final GeminiApiService gemini;
    private final PromptTemplateService prompts;

    public String generateSummary() {
        String mockStats = """
        totalEmployees=120
        pendingChecklists=34
        verificationRequired=12
        """;

        return gemini.generateText(prompts.hrDashboardPrompt(mockStats));
    }
}
