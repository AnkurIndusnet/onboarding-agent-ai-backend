package com.example.onboardingAgent.onboardingAgent.security.service;


import com.example.onboardingAgent.onboardingAgent.employee.repository.OtpRepository;
import com.example.onboardingAgent.onboardingAgent.model.OtpVerification;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository otpRepository;
    private final MailService mailService;
    private final PasswordEncoder encoder;

    public void sendOtp(String email) {

        String otp = String.valueOf(
                ThreadLocalRandom.current().nextInt(100000, 999999)
        );

        OtpVerification entity = new OtpVerification();
        entity.setEmail(email);
        entity.setOtpHash(encoder.encode(otp));
        entity.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        entity.setVerified(false);

        otpRepository.save(entity);
        mailService.sendOtp(email, otp);
    }

    public void verifyOtp(String email, String otp) {

        OtpVerification record = otpRepository
                .findTopByEmailOrderByExpiresAtDesc(email)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (record.isVerified())
            throw new RuntimeException("OTP already used");

        if (record.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new RuntimeException("OTP expired");

        if (!encoder.matches(otp, record.getOtpHash()))
            throw new RuntimeException("Invalid OTP");

        record.setVerified(true);
        otpRepository.save(record);
    }
}
