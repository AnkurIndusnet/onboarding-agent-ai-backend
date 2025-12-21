package com.example.onboardingAgent.onboardingAgent.employee.service.serviceImpl;

import com.example.onboardingAgent.onboardingAgent.hr.service.GeminiApiService;
import com.example.onboardingAgent.onboardingAgent.ai.PromptTemplateService;
import com.example.onboardingAgent.onboardingAgent.employee.service.ReadinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadinessServiceImpl implements ReadinessService {

    private final GeminiApiService gemini;
    private final PromptTemplateService prompts;

    @Override
    public String calculate(String checklistJson) {
        return gemini.generateText(prompts.readinessPrompt(checklistJson));
    }
}
