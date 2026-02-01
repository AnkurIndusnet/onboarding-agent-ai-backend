package com.example.onboardingAgent.onboardingAgent.employee.service;

import com.example.onboardingAgent.onboardingAgent.security.dto.response.ChecklistTaskFieldResponseDTO;
import com.example.onboardingAgent.onboardingAgent.security.dto.response.ChecklistTaskResponseDTO;

import java.util.List;

public interface ChecklistService {
    List<ChecklistTaskResponseDTO> generateChecklist(String role,String userEmail);
    List<ChecklistTaskFieldResponseDTO> getFieldsByTaskId(Long taskId);
}

