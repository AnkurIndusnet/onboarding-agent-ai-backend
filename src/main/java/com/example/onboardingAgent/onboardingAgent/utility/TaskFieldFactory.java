package com.example.onboardingAgent.onboardingAgent.utility;

import com.example.onboardingAgent.onboardingAgent.enums.TaskFieldType;
import com.example.onboardingAgent.onboardingAgent.model.ChecklistTaskEntity;
import com.example.onboardingAgent.onboardingAgent.model.ChecklistTaskFieldEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskFieldFactory {

    public List<ChecklistTaskFieldEntity> buildFields(
            ChecklistTaskEntity task
    ) {
        return switch (task.getType()) {

            case BANK_DOCUMENT -> List.of(
                    create(task, TaskFieldType.BANK_IFSC, "IFSC Code"),
                    create(task, TaskFieldType.BANK_ACCOUNT_NO, "Account Number"),
                    create(task, TaskFieldType.BANK_ACCOUNT_HOLDER, "Account Holder Name"),
                    create(task,TaskFieldType.BRANCH_NAME,"Branch Name")
            );

            case PERSONAL_DOCUMENT -> List.of(
                    create(task, TaskFieldType.PAN_NUMBER, "PAN Number"),
                    create(task, TaskFieldType.AADHAAR_NUMBER, "Aadhaar Number"),
                    create(task, TaskFieldType.NDA_DOCUMENT, "NDA Document")
            );

            case SETUP -> List.of(
                    create(task, TaskFieldType.EMAIL_ID, "Company Email ID"),
                    create(task, TaskFieldType.LAPTOP_SERIAL, "Laptop Serial Number"),
                    create(task,TaskFieldType.ACCESS_CARD,"Access Card Number")
            );

            case ORIENTATION -> List.of(
                    create(task, TaskFieldType.TRAINING_ACK, "Training Acknowledgement"),
                    create(task, TaskFieldType.POLICY_ACK, "Policy Acknowledgement")
            );

            case ADMIN -> List.of(
                    create(task, TaskFieldType.OFFER_LETTER, "Offer Letter"),
                    create(task, TaskFieldType.JOINING_FORM, "Joining Form")
            );

            default -> List.of();
        };
    }

    private ChecklistTaskFieldEntity create(
            ChecklistTaskEntity task,
            TaskFieldType type,
            String label
    ) {
        ChecklistTaskFieldEntity f = new ChecklistTaskFieldEntity();
        f.setTask(task);
        f.setFieldType(type);
        f.setLabel(label);
        f.setInputType("text");
        f.setRequired(true);
        f.setReadOnly(false);
        f.setValue("");
        return f;
    }
}
