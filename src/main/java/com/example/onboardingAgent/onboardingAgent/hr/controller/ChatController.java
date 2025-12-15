package com.example.onboardingAgent.onboardingAgent.hr.controller;

import com.example.onboardingAgent.onboardingAgent.hr.dto.request.SampleRequestDto;
import com.example.onboardingAgent.onboardingAgent.hr.service.GeminiApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
   private final GeminiApiService geminiApiService;

    @PostMapping("/message")
    public String getChatMessage(@RequestBody SampleRequestDto requestDto) {

            return geminiApiService.generateText(
                    requestDto.getQuestion()
            );

    }
}