package com.example.onboardingAgent.onboardingAgent.ai;

import org.springframework.stereotype.Service;

@Service
public class PromptTemplateService {

    public String checklistPrompt(String role) {
        return """
    You are an enterprise HR onboarding assistant.

    Generate a COARSE-GRAINED onboarding checklist for role: %s.

    IMPORTANT:
    - Generate AT MOST 5 tasks.
    - Each task must map to ONE of the following canonical buckets ONLY.
    - Do NOT create tasks outside these buckets.
    - Do NOT split tasks further.

    CANONICAL TASK BUCKETS (use these EXACTLY):
    1. Bank Details
    2. Identity Documents
    3. IT & Workspace Setup
    4. Orientation & Introductions
    5. Policies & Acknowledgements

    TASK TYPE MAPPING:
    - Bank Details → BANK_DOCUMENT
    - Identity Documents → PERSONAL_DOCUMENT
    - IT & Workspace Setup → SETUP
    - Orientation & Introductions → ORIENTATION
    - Policies & Acknowledgements → ADMIN

    PRIORITY RULES:
    - Bank Details, Identity Documents → HIGH
    - IT & Workspace Setup → HIGH
    - Orientation → MEDIUM
    - Policies → MEDIUM

    OUTPUT RULES:
    - Return ONLY valid JSON
    - No markdown
    - No explanations

    JSON format:
    [
      {
        "task": "Bank Details",
        "type": "BANK_DOCUMENT",
        "priority": "HIGH"
      }
    ]
    """.formatted(role);
    }


    public String ocrValidationPrompt(String docType, String text) {

        return """
You are a STRICT document validation engine.
You MUST validate OCR text using the rules below.
DO NOT guess. DO NOT auto-correct.
If any rule fails, mark valid=false.

----------------------------------
DOCUMENT TYPE: %s
----------------------------------

VALIDATION RULES:

1. NAME
- Must contain only alphabets and spaces
- Length >= 3 characters

2. DATE OF BIRTH (DOB)
- Format: DD-MM-YYYY or DD/MM/YYYY
- Must be a real calendar date
- Must NOT be a future date


3. DOCUMENT NUMBER FORMAT

IF DOCUMENT TYPE = AADHAAR:
- Must be exactly 12 digits
- Regex: ^\\d{12}$
- Person must be at least 6 years old

IF DOCUMENT TYPE = PAN:
- Format: 5 uppercase letters, 4 digits, 1 uppercase letter
- Regex: ^[A-Z]{5}[0-9]{4}[A-Z]{1}$
- Person must be at least 18 years old

IF DOCUMENT TYPE = PASSPORT (INDIA):
- Format: 1 uppercase letter followed by 7 digits
- Regex: ^[A-Z][0-9]{7}$
- Person must be at least 18 years old

----------------------------------
DECISION RULES:

- If ANY required field is missing → valid = false
- If ANY format rule fails → valid = false
- If DOB invalid → valid = false
- DO NOT infer missing values
- DO NOT hallucinate values

----------------------------------
RESPONSE FORMAT (STRICT JSON ONLY):

{
  "valid": true | false,
  "issues": [
    "Describe each validation failure clearly.bBut no need to tell internal logic."
  ],
  "extractedFields": {
    "name": "",
    "dob": "",
    "id": ""
  }
}

----------------------------------
OCR TEXT:
%s
----------------------------------
""".formatted(docType, text);
    }


    public String readinessPrompt(String checklistJson) {
        return """
        Calculate readiness score based on checklist.

        Return STRICT JSON:
        {
          "score": 0,
          "risks": [],
          "hrActionRequired": false
        }

        CHECKLIST:
        %s
        """.formatted(checklistJson);
    }

    public String hrDashboardPrompt(String rawStats) {
        return """
        You are an HR analyst.
        Analyze onboarding data and suggest actions.

        DATA:
        %s
        """.formatted(rawStats);
    }

    public String formatHrMessage(String payload) {
        return """
        Rewrite this into a professional HR communication:

        %s
        """.formatted(payload);
    }
}
