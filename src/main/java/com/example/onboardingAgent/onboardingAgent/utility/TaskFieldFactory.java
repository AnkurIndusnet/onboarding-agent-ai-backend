package com.example.onboardingAgent.onboardingAgent.utility;

import com.example.onboardingAgent.onboardingAgent.enums.TaskFieldDefinition;
import com.example.onboardingAgent.onboardingAgent.model.ChecklistTaskEntity;
import com.example.onboardingAgent.onboardingAgent.model.ChecklistTaskFieldEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class TaskFieldFactory {

    public List<ChecklistTaskFieldEntity> buildFields(
            ChecklistTaskEntity task
    ) {

        return Arrays.stream(TaskFieldDefinition.values())
                .filter(def -> def.getTaskType() == task.getType())
                .map(def -> toEntity(task, def))
                .toList();
    }

    private ChecklistTaskFieldEntity toEntity(
            ChecklistTaskEntity task,
            TaskFieldDefinition def
    ) {
        ChecklistTaskFieldEntity f = new ChecklistTaskFieldEntity();
        f.setTask(task);
        f.setLabel(def.getLabel());
        f.setInputType(def.getInputType().name());
        f.setRequired(def.isRequired());
        f.setReadOnly(def.isReadOnly());
        f.setValue("");
        f.setStatus(def.getStatus());
        return f;
    }
}
