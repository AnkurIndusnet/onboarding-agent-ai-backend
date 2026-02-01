package com.example.onboardingAgent.onboardingAgent.model;

import com.example.onboardingAgent.onboardingAgent.enums.Priority;
import com.example.onboardingAgent.onboardingAgent.enums.TaskType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "onboarding_checklist_task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChecklistTaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(nullable = false)
    private String task;

    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(nullable = false)
    private LocalDateTime askDateTime;

    private LocalDateTime submissionDateTime;

    @Column(nullable = false)
    private String userId; // or onboardingId / employeeId

    @OneToMany(
            mappedBy = "task",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ChecklistTaskFieldEntity> fields;
}
