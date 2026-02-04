package com.example.onboardingAgent.onboardingAgent.employee.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TaskSubmitRequestDTO {
    private Long taskId;
    private List<FieldValueDTO> values;

    @Data
    public static class FieldValueDTO {
        private Long fieldId;
        private String value;
    }
}
