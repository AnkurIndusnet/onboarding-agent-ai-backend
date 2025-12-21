package com.example.onboardingAgent.onboardingAgent.employee.controller;

import com.example.onboardingAgent.onboardingAgent.employee.dto.request.ChecklistGenerateRequest;
import com.example.onboardingAgent.onboardingAgent.employee.service.ChecklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/checklist")
@RequiredArgsConstructor
public class ChecklistController {

    private final ChecklistService checklistService;

    @PostMapping("/generate")
    public String generate(@RequestBody ChecklistGenerateRequest req) {
        return checklistService.generateChecklist(req.getRole());
    }
}
