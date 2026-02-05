package com.example.onboardingAgent.onboardingAgent.security.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChecklistTaskFieldResponseDTO {

    private Long taskId;
    private Long fieldId;
    private String label;
    private String type;
    private Boolean required;
    private Boolean readOnly;
    private String value;
    private String status;
}