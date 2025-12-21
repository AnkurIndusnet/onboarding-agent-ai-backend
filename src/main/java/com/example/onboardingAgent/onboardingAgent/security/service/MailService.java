package com.example.onboardingAgent.onboardingAgent.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendOtp(String to, String otp) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject("Onboarding Login OTP");
        mail.setText("""
Hello,

Your OTP for first-time onboarding login is:

%s

This OTP is valid for 5 minutes.
If you did not request this, please contact HR.

Regards,
Onboarding Team
""".formatted(otp));

        mailSender.send(mail);
    }
}
