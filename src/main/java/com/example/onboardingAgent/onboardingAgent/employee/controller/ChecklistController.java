package com.example.onboardingAgent.onboardingAgent.employee.controller;

import com.example.onboardingAgent.onboardingAgent.employee.dto.request.ChecklistGenerateRequest;
import com.example.onboardingAgent.onboardingAgent.employee.dto.request.TaskSubmitRequestDTO;
import com.example.onboardingAgent.onboardingAgent.employee.dto.response.OcrValidationResponseDTO;
import com.example.onboardingAgent.onboardingAgent.employee.service.ChecklistService;
import com.example.onboardingAgent.onboardingAgent.security.dto.response.ChecklistTaskFieldResponseDTO;
import com.example.onboardingAgent.onboardingAgent.security.dto.response.ChecklistTaskResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @GetMapping("/{taskId}/fields")
    public ResponseEntity<List<ChecklistTaskFieldResponseDTO>> getTaskFields(
            @PathVariable Long taskId
    ) {
        return ResponseEntity.ok(
                checklistService.getFieldsByTaskId(taskId)
        );
    }

    @GetMapping("/fetchCheckList")
    public ResponseEntity<List<ChecklistTaskResponseDTO>> fetchChecklist(
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(
                checklistService.fetchChecklist(userEmail)
        );
    }


    @PostMapping("/submit")
    public ResponseEntity<?> submitTask(
            @RequestBody TaskSubmitRequestDTO request
    ) {
        checklistService.submitTask(request);
        return ResponseEntity.ok().build();
    }

}
