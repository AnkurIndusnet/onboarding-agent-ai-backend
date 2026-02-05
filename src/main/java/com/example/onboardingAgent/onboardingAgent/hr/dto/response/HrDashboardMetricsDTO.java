package com.example.onboardingAgent.onboardingAgent.hr.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HrDashboardMetricsDTO {

    private long activeEmployees;
    private long pendingChecklists;
    private long verificationRequired;
    private long unassignedNewHires;
}
