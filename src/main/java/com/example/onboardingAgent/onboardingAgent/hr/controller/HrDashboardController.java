package com.example.onboardingAgent.onboardingAgent.hr.controller;

import com.example.onboardingAgent.onboardingAgent.hr.dto.response.HiringTrendDTO;
import com.example.onboardingAgent.onboardingAgent.hr.dto.response.HrDashboardMetricsDTO;
import com.example.onboardingAgent.onboardingAgent.hr.service.HrDashboardService;
import com.example.onboardingAgent.onboardingAgent.hr.service.HrHiringTrendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hr/dashboard")
@RequiredArgsConstructor
public class HrDashboardController {

    private final HrDashboardService service;
    private final HrHiringTrendService hiringTrendService;

    @GetMapping("/metrics")
    public ResponseEntity<HrDashboardMetricsDTO> getMetrics() {
        return ResponseEntity.ok(service.getMetrics());
    }

    @GetMapping("/hiring-trend")
    public ResponseEntity<List<HiringTrendDTO>> getHiringTrend(
            @RequestParam int year
    ) {
        return ResponseEntity.ok(
                hiringTrendService.getHiringTrend(year)
        );
    }
}
