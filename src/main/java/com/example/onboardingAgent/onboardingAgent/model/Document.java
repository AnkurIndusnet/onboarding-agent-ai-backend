package com.example.onboardingAgent.onboardingAgent.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "document")
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long checklistId;
    private String documentType;
    private String status;
    private String fileName;
}
