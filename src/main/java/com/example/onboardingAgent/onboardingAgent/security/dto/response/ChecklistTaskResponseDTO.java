package com.example.onboardingAgent.onboardingAgent.security.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChecklistTaskResponseDTO {
    private Long taskId;
    private String task;
    private String type;
    private String priority;
    private LocalDateTime askDateTime;
    private LocalDateTime submissionDateTime;
}
