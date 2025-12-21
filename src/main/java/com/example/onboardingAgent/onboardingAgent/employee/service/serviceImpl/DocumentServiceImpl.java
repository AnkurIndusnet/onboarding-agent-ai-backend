package com.example.onboardingAgent.onboardingAgent.employee.service.serviceImpl;

import com.example.onboardingAgent.onboardingAgent.hr.service.GeminiApiService;
import com.example.onboardingAgent.onboardingAgent.ai.PromptTemplateService;
import com.example.onboardingAgent.onboardingAgent.employee.dto.request.OcrValidationRequest;
import com.example.onboardingAgent.onboardingAgent.employee.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final GeminiApiService gemini;
    private final PromptTemplateService prompts;

    @Override
    public String validate(OcrValidationRequest req) {
        return gemini.generateText(
                prompts.ocrValidationPrompt(
                        req.getDocumentType(),
                        req.getExtractedText()
                )
        );
    }
}
