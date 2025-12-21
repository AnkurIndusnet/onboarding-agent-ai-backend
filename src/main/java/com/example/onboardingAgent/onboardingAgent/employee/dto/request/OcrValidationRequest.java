package com.example.onboardingAgent.onboardingAgent.employee.dto.request;

import lombok.Data;

@Data
public class OcrValidationRequest {
    private String documentType;
    private String extractedText;
}
