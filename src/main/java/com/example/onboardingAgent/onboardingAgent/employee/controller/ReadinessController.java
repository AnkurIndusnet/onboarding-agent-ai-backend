package com.example.onboardingAgent.onboardingAgent.employee.controller;

import com.example.onboardingAgent.onboardingAgent.employee.dto.request.ReadinessCalculateRequest;
import com.example.onboardingAgent.onboardingAgent.employee.service.ReadinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/readiness")
@RequiredArgsConstructor
public class ReadinessController {

    private final ReadinessService service;

    @PostMapping("/calculate")
    public String calculate(@RequestBody ReadinessCalculateRequest req) {
        return service.calculate(req.getChecklistJson());
    }
}
