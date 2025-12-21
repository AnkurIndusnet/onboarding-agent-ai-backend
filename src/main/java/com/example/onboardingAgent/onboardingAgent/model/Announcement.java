package com.example.onboardingAgent.onboardingAgent.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "announcement")
@Data
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String scope;     // ALL / SPECIFIC
    private String message;
    private LocalDateTime createdAt;
}
