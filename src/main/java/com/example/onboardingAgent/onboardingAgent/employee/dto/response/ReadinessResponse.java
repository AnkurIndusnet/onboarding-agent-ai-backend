package com.example.onboardingAgent.onboardingAgent.employee.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class ReadinessResponse {
    private int score;
    private List<String> risks;
    private boolean hrActionRequired;
}
