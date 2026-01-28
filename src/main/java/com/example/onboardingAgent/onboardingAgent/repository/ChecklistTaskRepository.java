package com.example.onboardingAgent.onboardingAgent.repository;

import com.example.onboardingAgent.onboardingAgent.model.ChecklistTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistTaskRepository
        extends JpaRepository<ChecklistTaskEntity, Long> {
}
