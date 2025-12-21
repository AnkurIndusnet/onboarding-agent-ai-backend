package com.example.onboardingAgent.onboardingAgent.ai.controller;

import com.example.onboardingAgent.onboardingAgent.ai.AiStreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class StreamingController {

    private final AiStreamingService streamingService;

    @GetMapping(value = "/stream", produces = "text/event-stream")
    public Flux<String> stream(@RequestParam String prompt) {
        return streamingService.stream(prompt);
    }
}
