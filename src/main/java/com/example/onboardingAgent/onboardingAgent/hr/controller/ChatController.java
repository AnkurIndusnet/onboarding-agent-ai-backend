package com.example.onboardingAgent.onboardingAgent.hr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatClient chatClient;

    @GetMapping("/message")
    public String getChatMessage() {
        // Example interaction with the chat client
        String response = chatClient
                .prompt()
                .system("You are a test assistant")
                .user("Reply only with: Gemini connected")
                .call()
                .content();

        return response;
    }
}
