package com.example.onboardingAgent.onboardingAgent.security.dto.response;

import lombok.Data;

@Data
public class GeminiChecklistItemDTO {
    private String task;
    private String type;
    private String priority;
}
