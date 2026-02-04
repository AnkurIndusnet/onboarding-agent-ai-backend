package com.example.onboardingAgent.onboardingAgent.employee.service;

import com.example.onboardingAgent.onboardingAgent.employee.dto.request.OcrValidationRequest;
import com.example.onboardingAgent.onboardingAgent.employee.dto.response.OcrValidationResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    String validate(OcrValidationRequest request);
    OcrValidationResponseDTO validateDocument(
            MultipartFile file,
            String documentType
    ) throws Exception;
}
