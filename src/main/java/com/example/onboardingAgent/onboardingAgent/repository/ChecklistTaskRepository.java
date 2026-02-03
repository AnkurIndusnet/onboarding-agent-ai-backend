package com.example.onboardingAgent.onboardingAgent.repository;

import com.example.onboardingAgent.onboardingAgent.model.ChecklistTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistTaskRepository
        extends JpaRepository<ChecklistTaskEntity, Long> {
    List<ChecklistTaskEntity> findByUserIdOrderByAskDateTimeAsc(String empId);

}
