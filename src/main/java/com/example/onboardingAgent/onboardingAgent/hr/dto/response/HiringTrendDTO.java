package com.example.onboardingAgent.onboardingAgent.hr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HiringTrendDTO {
    private String month;
    private long hires;
}