package com.example.onboardingAgent.onboardingAgent.repository;

import com.example.onboardingAgent.onboardingAgent.model.ChecklistTaskFieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ChecklistTaskFieldRepository
        extends JpaRepository<ChecklistTaskFieldEntity, Long> {

    List<ChecklistTaskFieldEntity> findByTask_TaskId(Long taskId);
}