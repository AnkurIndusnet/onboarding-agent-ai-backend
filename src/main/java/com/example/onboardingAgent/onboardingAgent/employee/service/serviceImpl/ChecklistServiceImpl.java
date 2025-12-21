package com.example.onboardingAgent.onboardingAgent.employee.service.serviceImpl;

import com.example.onboardingAgent.onboardingAgent.ai.PromptTemplateService;
import com.example.onboardingAgent.onboardingAgent.employee.service.ChecklistService;
import com.example.onboardingAgent.onboardingAgent.hr.service.GeminiApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChecklistServiceImpl implements ChecklistService {

    private final GeminiApiService gemini;
    private final PromptTemplateService prompts;

    @Override
    public String generateChecklist(String role) {
        String prompt = prompts.checklistPrompt(role);
        String response = gemini.generateText(prompt);
        return response; // frontend parses initially
    }
}
