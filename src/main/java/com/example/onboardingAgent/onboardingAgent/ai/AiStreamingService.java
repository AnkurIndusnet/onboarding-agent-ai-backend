package com.example.onboardingAgent.onboardingAgent.ai;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class AiStreamingService {

    public Flux<String> stream(String prompt) {
        return Flux.just(prompt.split(" "));
    }
}
