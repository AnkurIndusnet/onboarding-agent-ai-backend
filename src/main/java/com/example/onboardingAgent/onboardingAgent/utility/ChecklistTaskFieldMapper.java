package com.example.onboardingAgent.onboardingAgent.utility;

import com.example.onboardingAgent.onboardingAgent.model.ChecklistTaskFieldEntity;
import com.example.onboardingAgent.onboardingAgent.security.dto.response.ChecklistTaskFieldResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ChecklistTaskFieldMapper {

    public ChecklistTaskFieldResponseDTO toResponse(
            ChecklistTaskFieldEntity entity
    ) {
        return ChecklistTaskFieldResponseDTO.builder()
                .taskId(entity.getTask().getTaskId())
                .fieldId(entity.getFieldId())
                .label(entity.getLabel())
                .type(entity.getInputType())
                .required(entity.getRequired())
                .readOnly(entity.getReadOnly())
                .value(entity.getValue())
                .build();
    }
}