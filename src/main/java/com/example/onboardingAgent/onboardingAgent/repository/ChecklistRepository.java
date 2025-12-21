package com.example.onboardingAgent.onboardingAgent.repository;

import com.example.onboardingAgent.onboardingAgent.model.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistRepository
        extends JpaRepository<ChecklistItem, Long> {
}
