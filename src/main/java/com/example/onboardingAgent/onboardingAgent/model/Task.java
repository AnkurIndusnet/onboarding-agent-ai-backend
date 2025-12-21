package com.example.onboardingAgent.onboardingAgent.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "task")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;
    private String type;      // DOCUMENT / ESIGN / TRAINING
    private String status;    // ASSIGNED / DONE
}
