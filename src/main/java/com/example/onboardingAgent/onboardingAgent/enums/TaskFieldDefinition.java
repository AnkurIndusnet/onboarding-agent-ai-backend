package com.example.onboardingAgent.onboardingAgent.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskFieldDefinition {
    // -------- DOCUMENT TASKS --------
    PAN_CARD(
            TaskType.PERSONAL_DOCUMENT,
            "PAN Card",
            FieldInputType.DOCUMENT,
            true,
            false,
            MasterTaskStatus.PENDING
    ),
    AADHAAR_CARD(
            TaskType.PERSONAL_DOCUMENT,
            "Aadhaar Card",
            FieldInputType.DOCUMENT,
            true,
            false,
            MasterTaskStatus.PENDING
    ),
//    NDA_DOCUMENT(
//            TaskType.PERSONAL_DOCUMENT,
//            "NDA / Confidentiality Agreement",
//            FieldInputType.DOCUMENT,
//            true,
//            false,
//            MasterTaskStatus.PENDING
//    ),
    MARKSHEET(
            TaskType.PERSONAL_DOCUMENT,
            "Educational Marksheet",
            FieldInputType.DOCUMENT,
            true,
            false,
        MasterTaskStatus.PENDING
    ),
    EXPERIENCE_LETTER(
            TaskType.PERSONAL_DOCUMENT,
            "Experience Letter from College / Previous Employer",
            FieldInputType.DOCUMENT,
            false,
            false,
            MasterTaskStatus.NOT_REQUIRED
    ),

    // -------- BANK DETAILS --------
    BANK_IFSC(
            TaskType.BANK_DOCUMENT,
            "IFSC Code",
            FieldInputType.TEXT,
            true,
            false,
            MasterTaskStatus.PENDING
    ),
    BANK_ACCOUNT_NO(
            TaskType.BANK_DOCUMENT,
            "Account Number",
            FieldInputType.TEXT,
            true,
            false,
            MasterTaskStatus.PENDING
    ),
    BANK_ACCOUNT_HOLDER(
            TaskType.BANK_DOCUMENT,
            "Account Holder Name",
            FieldInputType.TEXT,
            true,
            false,
            MasterTaskStatus.PENDING
    ),

    BRANCH_NAME(
            TaskType.BANK_DOCUMENT,
            "Branch Name",
            FieldInputType.TEXT,
            false,
            false,
            MasterTaskStatus.PENDING
    ),

    // -------- SETUP --------
    EMAIL_ID(
            TaskType.SETUP,
            "Company Email",
            FieldInputType.TEXT,
            true,
            false,
            MasterTaskStatus.NOT_REQUIRED
    ),
    LAPTOP_SERIAL(
            TaskType.SETUP,
            "Laptop Serial Number",
            FieldInputType.TEXT,
            true,
            false,
            MasterTaskStatus.PENDING
    ),
    ACCESS_CARD(
            TaskType.SETUP,
            "Access Card Number",
            FieldInputType.TEXT,
            false,
            false,
            MasterTaskStatus.PENDING
    ),

    // -------- ORIENTATION --------
    TRAINING_ACK(
            TaskType.ORIENTATION,
            "Training Acknowledgement",
            FieldInputType.CHECKBOX,
            true,
            false,
            MasterTaskStatus.PENDING
    ),
    POLICY_ACK(
            TaskType.ORIENTATION,
            "Policy Acknowledgement",
            FieldInputType.CHECKBOX,
            true,
            false,
            MasterTaskStatus.PENDING
    ),
    // -------- ADMIN --------
    OFFER_LETTER(
            TaskType.ADMIN,
            "Offer Letter",
            FieldInputType.DOCUMENT,
            true,
            true,
            MasterTaskStatus.PENDING
    ),
    JOINING_FORM(
            TaskType.ADMIN,
            "Joining Form",
            FieldInputType.DOCUMENT,
            true,
            false,
            MasterTaskStatus.PENDING
    )
    ;

    private final TaskType taskType;
    private final String label;
    private final FieldInputType inputType;
    private final boolean required;
    private final boolean readOnly;
    private final MasterTaskStatus status;
}
