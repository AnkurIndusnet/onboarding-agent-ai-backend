package com.example.onboardingAgent.onboardingAgent.employee.controller;

import com.example.onboardingAgent.onboardingAgent.ai.ConversationMemoryService;
import com.example.onboardingAgent.onboardingAgent.hr.dto.request.SampleRequestDto;
import com.example.onboardingAgent.onboardingAgent.hr.service.GeminiApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/employee/chat")
public class EmployeeChatController {
private final GeminiApiService geminiApiService;
    private final ConversationMemoryService memory;
    @PostMapping("/message")
    public String chat(
            @RequestParam String userId,
            @RequestBody SampleRequestDto dto
    ) {
        memory.add(userId, "User: " + dto.getQuestion());

        String prompt = memory.context(userId);
        String response = geminiApiService.generateText(prompt);

        memory.add(userId, "AI: " + response);
        return response;
    }

}
