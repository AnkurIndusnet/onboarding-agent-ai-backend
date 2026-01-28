package com.example.onboardingAgent.onboardingAgent.employee.controller;

import com.example.onboardingAgent.onboardingAgent.employee.dto.request.ChecklistGenerateRequest;
import com.example.onboardingAgent.onboardingAgent.employee.service.ChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/checklist")
@RequiredArgsConstructor
public class ChecklistController {

    private final ChecklistService checklistService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateChecklist(@RequestBody ChecklistGenerateRequest req,Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(
                checklistService.generateChecklist(req.getRole(),userEmail)
        );    }
}
