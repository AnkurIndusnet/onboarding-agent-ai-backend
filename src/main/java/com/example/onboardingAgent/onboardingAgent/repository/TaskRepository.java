package com.example.onboardingAgent.onboardingAgent.repository;

import com.example.onboardingAgent.onboardingAgent.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT COUNT(t) FROM Task t WHERE t.employeeId = :employeeId AND t.status = :status")
    int countByEmployeeIdAndStatus(String employeeId, String status);
}
