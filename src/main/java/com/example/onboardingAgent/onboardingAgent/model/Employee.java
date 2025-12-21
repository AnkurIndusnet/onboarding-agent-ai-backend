package com.example.onboardingAgent.onboardingAgent.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    private String role;          // EMPLOYEE / HR / MANAGER

    private String status;        // INVITED / ACTIVE / BLOCKED

    private boolean firstLogin;   // true until OTP verified

    private LocalDate joiningDate;

    private Integer readinessScore;
}
