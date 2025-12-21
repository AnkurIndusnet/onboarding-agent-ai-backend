package com.example.onboardingAgent.onboardingAgent.employee.controller;

import com.example.onboardingAgent.onboardingAgent.employee.dto.request.OcrValidationRequest;
import com.example.onboardingAgent.onboardingAgent.employee.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService service;

    @PostMapping("/validate")
    public String validate(@RequestBody OcrValidationRequest request) {
        return service.validate(request);
    }
}
