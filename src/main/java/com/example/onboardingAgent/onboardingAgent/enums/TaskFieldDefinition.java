package com.example.onboardingAgent.onboardingAgent.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskFieldDefinition {
    // -------- DOCUMENT TASKS --------
    PAN_CARD(
            TaskType.IDENTITY_DOCUMENTS,
            "PAN Card",
            FieldInputType.DOCUMENT,
            true,
            false,
            MasterTaskStatus.PENDING
    ),
    AADHAAR_CARD(
            TaskType.IDENTITY_DOCUMENTS,
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
    XMARKSHEET(
            TaskType.EDUCATION_DOCUMENTS,
            "Class 10th Marksheet",
            FieldInputType.DOCUMENT,
            true,
            false,
        MasterTaskStatus.PENDING
    ),
    XIIMARKSHEET(
            TaskType.EDUCATION_DOCUMENTS,
            "Class 12th Marksheet",
            FieldInputType.DOCUMENT,
            true,
            false,
            MasterTaskStatus.PENDING
    ),
    GRADUATIONMARKSHEET(
            TaskType.EDUCATION_DOCUMENTS,
            "Graduation Marksheet",
            FieldInputType.DOCUMENT,
            true,
            false,
            MasterTaskStatus.PENDING
    ),
    MASTERMARKSHEET(
            TaskType.EDUCATION_DOCUMENTS,
            "Master Marksheet",
            FieldInputType.DOCUMENT,
            false,
            false,
            MasterTaskStatus.PENDING
    ),
    OFFER_LETTER(
            TaskType.WORK_EXPERIENCE_DOCUMENTS,
            "Offer Letter from Previous Employer",
            FieldInputType.DOCUMENT,
            true,
            false,
            MasterTaskStatus.PENDING
    ),
    RELEASE_LETTER(
            TaskType.WORK_EXPERIENCE_DOCUMENTS,
            "Relieving / Release Letter from Previous Employer",
            FieldInputType.DOCUMENT,
            true,
            false,
            MasterTaskStatus.PENDING
    ),
    PASSPORT_PHOTO(
            TaskType.PASSPORT_PHOTO,
            "Passport Size Photograph",
            FieldInputType.DOCUMENT,
            true,
            false,
            MasterTaskStatus.PENDING
    ),


    ;

    private final TaskType taskType;
    private final String label;
    private final FieldInputType inputType;
    private final boolean required;
    private final boolean readOnly;
    private final MasterTaskStatus status;
}
