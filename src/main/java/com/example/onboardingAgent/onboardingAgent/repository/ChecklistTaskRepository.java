package com.example.onboardingAgent.onboardingAgent.repository;

import com.example.onboardingAgent.onboardingAgent.enums.MasterTaskStatus;
import com.example.onboardingAgent.onboardingAgent.model.ChecklistTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistTaskRepository
        extends JpaRepository<ChecklistTaskEntity, Long> {
    List<ChecklistTaskEntity> findByUserIdOrderByAskDateTimeAsc(String empId);

    int countByUserIdAndSubmissionDateTimeIsNotNull(String empId);

    int countByUserIdAndSubmissionDateTimeIsNull(String empId);

    int countByUserIdAndStatusNot(
            String empId,
            MasterTaskStatus status
    );


}
