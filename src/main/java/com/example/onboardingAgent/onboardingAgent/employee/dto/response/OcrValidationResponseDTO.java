package com.example.onboardingAgent.onboardingAgent.employee.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class OcrValidationResponseDTO {

    private boolean valid;
    private List<String> issues;
    private ExtractedFields extractedFields;

    @Data
    public static class ExtractedFields {
        private String name;
        private String dob;
        private String id;
    }
}
