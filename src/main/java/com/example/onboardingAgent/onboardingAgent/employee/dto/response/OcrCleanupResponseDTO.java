package com.example.onboardingAgent.onboardingAgent.employee.dto.response;

import lombok.Data;

@Data
public class OcrCleanupResponseDTO {
    private String documentType;
    private String extractedText;
}
