package com.example.onboardingAgent.onboardingAgent.employee.dto.response;

import lombok.Data;

@Data
public class ChecklistItemResponse {
    private String task;
    private String type;
    private String priority;
}
