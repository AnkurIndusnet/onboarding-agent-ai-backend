package com.example.onboardingAgent.onboardingAgent.employee.dto.response;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class OcrValidationResponse {
    private boolean valid;
    private List<String> issues;
    private Map<String,String> extractedFields;
}
