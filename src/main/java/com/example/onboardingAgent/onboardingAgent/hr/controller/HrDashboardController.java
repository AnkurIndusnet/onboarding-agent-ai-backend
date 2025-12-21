package com.example.onboardingAgent.onboardingAgent.hr.controller;

import com.example.onboardingAgent.onboardingAgent.hr.service.HrDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hr/dashboard")
@RequiredArgsConstructor
public class HrDashboardController {

    private final HrDashboardService service;

    @GetMapping("/summary")
    public String summary() {
        return service.generateSummary();
    }
}
