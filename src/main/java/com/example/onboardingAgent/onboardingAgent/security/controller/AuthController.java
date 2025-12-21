package com.example.onboardingAgent.onboardingAgent.security.controller;


import com.example.onboardingAgent.onboardingAgent.model.Employee;
import com.example.onboardingAgent.onboardingAgent.repository.EmployeeRepository;
import com.example.onboardingAgent.onboardingAgent.security.JwtUtil;
import com.example.onboardingAgent.onboardingAgent.security.dto.request.LoginRequest;
import com.example.onboardingAgent.onboardingAgent.security.service.OtpService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final EmployeeRepository employeeRepository;
    private final OtpService otpService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody @Valid LoginRequest request) {
        String email = request.getEmail();
        Employee emp = employeeRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found. Contact HR.")
                );

//        if (emp.isFirstLogin()) {
//            otpService.sendOtp(email);
//            return Map.of("message", "OTP sent to registered email");
//        }

        return Map.of(
                "token",
                jwtUtil.generateToken(emp.getEmail(), emp.getRole())
        );
    }

    @PostMapping("/verify-otp")
    public Map<String, String> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp
    ) {

        otpService.verifyOtp(email, otp);

        Employee emp = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        emp.setFirstLogin(false);
        emp.setStatus("ACTIVE");
        employeeRepository.save(emp);

        return Map.of(
                "token",
                jwtUtil.generateToken(emp.getEmail(), emp.getRole())
        );
    }
}
