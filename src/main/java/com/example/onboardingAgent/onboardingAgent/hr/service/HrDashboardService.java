package com.example.onboardingAgent.onboardingAgent.hr.service;

import com.example.onboardingAgent.onboardingAgent.hr.dto.response.HrDashboardMetricsDTO;
import com.example.onboardingAgent.onboardingAgent.hr.service.GeminiApiService;
import com.example.onboardingAgent.onboardingAgent.ai.PromptTemplateService;
import com.example.onboardingAgent.onboardingAgent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HrDashboardService {

    private final UserRepository userRepository;

    public HrDashboardMetricsDTO getMetrics() {

        return HrDashboardMetricsDTO.builder()
                .activeEmployees(userRepository.countBy())
                .pendingChecklists(
                        userRepository.countByDocumentRequiredTrueAndVerificationRequiredFalse()
                )
                .verificationRequired(
                        userRepository.countByDocumentRequiredFalseAndVerificationRequiredTrue()
                )
                .unassignedNewHires(
                        userRepository.countByManagerIsNull()
                )
                .build();
    }
}
