package com.example.onboardingAgent.onboardingAgent.employee.service.serviceImpl;

import com.example.onboardingAgent.onboardingAgent.ai.PromptTemplateService;
import com.example.onboardingAgent.onboardingAgent.employee.dto.request.OcrValidationRequest;
import com.example.onboardingAgent.onboardingAgent.employee.dto.response.OcrCleanupResponseDTO;
import com.example.onboardingAgent.onboardingAgent.employee.dto.response.OcrValidationResponseDTO;
import com.example.onboardingAgent.onboardingAgent.employee.service.DocumentService;
import com.example.onboardingAgent.onboardingAgent.hr.service.GeminiApiService;
import com.example.onboardingAgent.onboardingAgent.utility.FreeOcrService;
import com.example.onboardingAgent.onboardingAgent.utility.GeminiJsonExtractor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final GeminiApiService gemini;
    private final PromptTemplateService prompts;
    private final FreeOcrService freeOcrService;
    private final ObjectMapper objectMapper;

    /**
     * TEXT-ONLY VALIDATION (no OCR)
     * Used when extractedText is already available
     */
    @Override public String validate(OcrValidationRequest req) {
        return gemini.generateText( prompts.ocrValidationPrompt( req.getDocumentType(), req.getExtractedText() ) ); }

    /**
     * FULL FLOW:
     * Image/PDF -> OCR -> AI Cleanup -> AI Validation
     */

    @Override
    public OcrValidationResponseDTO validateDocument(
            MultipartFile file,
            String documentType
    ) {

        try {
            String rawOcrText = freeOcrService.extractText(file);
            log.info("Raw OCR Text: {}", rawOcrText);

            // 1️⃣ Cleanup
            String cleanupPrompt = prompts.ocrCleanupPrompt(rawOcrText);
            String cleanupResponse = gemini.generateText(cleanupPrompt);
            log.info("cleanupResponse: {}", cleanupResponse);

            String cleanupJson =
                    GeminiJsonExtractor.extractJsonObject(cleanupResponse);

            OcrCleanupResponseDTO cleanup =
                    objectMapper.readValue(
                            cleanupJson,
                            OcrCleanupResponseDTO.class
                    );

            // 2️⃣ Validation
            String validationPrompt =
                    prompts.ocrValidationPrompt(
                            cleanup.getDocumentType(),
                            cleanup.getExtractedText()
                    );

            String validationResponse =
                    gemini.generateText(validationPrompt);
            log.info("validationResponse: {}", validationResponse);

            String validationJson =
                    GeminiJsonExtractor.extractJsonObject(validationResponse);

            log.info("validationJson: {}", validationJson);

            return objectMapper.readValue(
                    validationJson,
                    OcrValidationResponseDTO.class
            );

        } catch (Exception e) {
            throw new RuntimeException("Document validation failed", e);
        }
    }

}
