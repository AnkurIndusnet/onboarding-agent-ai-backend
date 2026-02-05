package com.example.onboardingAgent.onboardingAgent.utility;

import com.example.onboardingAgent.onboardingAgent.enums.MasterTaskStatus;
import com.example.onboardingAgent.onboardingAgent.enums.Priority;
import com.example.onboardingAgent.onboardingAgent.enums.TaskType;
import com.example.onboardingAgent.onboardingAgent.model.ChecklistTaskEntity;
import com.example.onboardingAgent.onboardingAgent.security.dto.response.ChecklistTaskResponseDTO;
import com.example.onboardingAgent.onboardingAgent.security.dto.response.GeminiChecklistItemDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ChecklistMapper {

    public ChecklistTaskEntity toEntity(
            GeminiChecklistItemDTO dto,
            String userId
    ) {
        ChecklistTaskEntity entity = new ChecklistTaskEntity();
        entity.setTask(dto.getTask());
        entity.setType(TaskType.valueOf(dto.getType()));
        entity.setPriority(Priority.valueOf(dto.getPriority()));
        entity.setAskDateTime(LocalDateTime.now());
        entity.setSubmissionDateTime(null);
        entity.setUserId(userId);
        entity.setStatus(MasterTaskStatus.PENDING);
        return entity;
    }

    public ChecklistTaskResponseDTO toResponse(ChecklistTaskEntity entity) {
        return ChecklistTaskResponseDTO.builder()
                .taskId(entity.getTaskId())
                .task(entity.getTask())
                .type(entity.getType().name())
                .priority(entity.getPriority().name())
                .askDateTime(entity.getAskDateTime())
                .submissionDateTime(entity.getSubmissionDateTime())
                .status(entity.getStatus().name())
                .build();
    }
}
