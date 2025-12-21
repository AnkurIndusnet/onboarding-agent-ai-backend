package com.example.onboardingAgent.onboardingAgent.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "checklist")
@Data
public class ChecklistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;
    private String task;
    private String type;       // DOCUMENT / FORM / TRAINING
    private String status;     // PENDING / COMPLETED / VERIFIED
}
