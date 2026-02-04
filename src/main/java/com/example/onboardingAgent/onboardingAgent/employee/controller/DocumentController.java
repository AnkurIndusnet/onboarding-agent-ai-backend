package com.example.onboardingAgent.onboardingAgent.employee.controller;

import com.example.onboardingAgent.onboardingAgent.employee.dto.request.OcrValidationRequest;
import com.example.onboardingAgent.onboardingAgent.employee.dto.response.OcrValidationResponseDTO;
import com.example.onboardingAgent.onboardingAgent.employee.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/employee/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService service;

    @PostMapping("/oldvalidate")
    public String validate(@RequestBody OcrValidationRequest request) {
        return service.validate(request);
    }

    @PostMapping(value = "/validate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OcrValidationResponseDTO> validateDocument(
            @RequestPart("file") MultipartFile file,
            @RequestParam("documentType") String documentType
    ) throws Exception {
        return ResponseEntity.ok(
                service.validateDocument(file, documentType)
        );
    }
}
