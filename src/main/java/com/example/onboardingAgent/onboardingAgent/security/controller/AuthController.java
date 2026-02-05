package com.example.onboardingAgent.onboardingAgent.security.controller;


import com.example.onboardingAgent.onboardingAgent.model.UserEntity;
import com.example.onboardingAgent.onboardingAgent.repository.ChecklistTaskRepository;
import com.example.onboardingAgent.onboardingAgent.repository.RoleRepository;
import com.example.onboardingAgent.onboardingAgent.repository.TaskRepository;
import com.example.onboardingAgent.onboardingAgent.repository.UserRepository;
import com.example.onboardingAgent.onboardingAgent.security.dto.request.SetPasswordRequest;
import com.example.onboardingAgent.onboardingAgent.security.dto.response.LoginResponse;
import com.example.onboardingAgent.onboardingAgent.security.dto.response.UserResponse;
import com.example.onboardingAgent.onboardingAgent.security.service.GoogleTokenVerifierService;
import com.example.onboardingAgent.onboardingAgent.security.service.JwtService;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final GoogleTokenVerifierService googleService;

    private final UserRepository userRepo;

    private final RoleRepository roleRepo;

    private final TaskRepository taskRepo;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;
    private final ChecklistTaskRepository checklistTaskRepository;

    @PostMapping("/google-login")
    public LoginResponse googleLogin(@RequestBody Map<String, String> body) {

        String token = body.get("token");
        GoogleIdToken.Payload payload = googleService.verify(token);

        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String googleId = payload.getSubject();

        UserEntity user = userRepo.findByEmail(email).orElseGet(() -> {
            UserEntity u = new UserEntity();
            u.setEmail(email);
            u.setName(name);
            u.setGoogleId(googleId);
            u.setRoleId(2); // EMPLOYEE
            u.setEmpId("EMP" + System.currentTimeMillis());
            u.setPasswordRequired(true);
            u.setDocumentRequired(true);
            u.setVerificationRequired(false);
            u.setDateOfJoining(new Date());
            return userRepo.save(u);
        });

        String jwt = jwtService.generateToken(
                user.getEmail(),
                user.getRoleId(),
                user.getEmpId()
        );

        return new LoginResponse(jwt, user.isPasswordRequired());
    }

    @GetMapping("/me")
    public UserResponse me(Authentication authentication) {

        String email = authentication.getName();
        UserEntity user = userRepo.findByEmail(email).orElseThrow();

        int pending = checklistTaskRepository.countByUserIdAndSubmissionDateTimeIsNull(user.getEmpId());
        int completed = checklistTaskRepository.countByUserIdAndSubmissionDateTimeIsNotNull(user.getEmpId());

        return new UserResponse(
                user.getName(),
                roleRepo.findRoleNameById(user.getRoleId()),
                user.getEmpId(),
                pending,
                completed,
                user.getLocation(),
                user.getDesignation()
        );
    }

    @PostMapping("/set-password")
    public LoginResponse setPassword(
            @RequestBody SetPasswordRequest request,
            Authentication authentication
    ) {

        String email = authentication.getName();

        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isPasswordRequired()) {
            throw new RuntimeException("Password already set");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPasswordRequired(false);
        userRepo.save(user);

        String jwt = jwtService.generateToken(
                user.getEmail(),
                user.getRoleId(),
                user.getEmpId()
        );

        return new LoginResponse(jwt, false);
    }

}
