package com.example.onboardingAgent.onboardingAgent.employee.repository;


import com.example.onboardingAgent.onboardingAgent.model.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository
        extends JpaRepository<OtpVerification, Long> {

    Optional<OtpVerification> findTopByEmailOrderByExpiresAtDesc(String email);
}
