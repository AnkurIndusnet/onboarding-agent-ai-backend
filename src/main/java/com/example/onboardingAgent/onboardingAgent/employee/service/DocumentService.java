package com.example.onboardingAgent.onboardingAgent.employee.service;

import com.example.onboardingAgent.onboardingAgent.employee.dto.request.OcrValidationRequest;

public interface DocumentService {
    String validate(OcrValidationRequest request);
}
