package com.example.onboardingAgent.onboardingAgent.model;

import com.example.onboardingAgent.onboardingAgent.enums.MasterTaskStatus;
import com.example.onboardingAgent.onboardingAgent.enums.TaskFieldType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "onboarding_checklist_task_field")
@Data
public class ChecklistTaskFieldEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fieldId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private ChecklistTaskEntity task;

    @Enumerated(EnumType.STRING)
    private TaskFieldType fieldType;

    private String label;
    private String inputType;   // text, number, date
    private Boolean required;
    private Boolean readOnly;
    private String value;
    private MasterTaskStatus status;
}
