package com.example.onboardingAgent.onboardingAgent.ai;

import org.springframework.stereotype.Service;

@Service
public class PromptTemplateService {

    public String checklistPrompt(String role) {
        return """
    You are an enterprise HR onboarding assistant.

    Generate a COARSE-GRAINED onboarding checklist for role: %s.Role can be either FRESHER or EXPERIENCED.

    IMPORTANT:
    - Generate AT MOST 5 tasks.
    - Each task must map to ONE of the following canonical buckets ONLY.
    - Do NOT create tasks outside these buckets.
    - Do NOT split tasks further.

    CANONICAL TASK BUCKETS FOR FRESHER ROLE (use these EXACTLY):
    1. Identity Documents
    2. Education Documents
    3. Passport Photograph
    
     CANONICAL TASK BUCKETS FOR EXPERIENCED ROLE(use these EXACTLY):
    1. Identity Documents
    2. Education Documents
    3. Work Experience Documents
    4. Passport Photograph
    

    TASK TYPE MAPPING:
    - Identity Documents → IDENTITY_DOCUMENTS
    - Education Documents → EDUCATION_DOCUMENTS
    - Work Experience Documents → WORK_EXPERIENCE_DOCUMENTS
    - Passport Photograph → PASSPORT_PHOTO

    PRIORITY RULES:
    - Based On Role All Documents are HIGH Priority

    OUTPUT RULES:
    - Return ONLY valid JSON
    - No markdown
    - No explanations

    JSON format:
    [
      {
        "task": "Identity Documents",
        "type": "IDENTITY_DOCUMENT",
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
- Only Mandatory for AADHAAR and PAN


3. DOCUMENT NUMBER FORMAT

IF DOCUMENT TYPE = AADHAAR:
- Must be exactly 12 digits
- Regex: ^\\d{12}$
- Person must be at least 6 years old
- Format can be - xxxx xxxx xxxx or xxxxxxxxxxxx

IF DOCUMENT TYPE = PAN:
- Format: 5 uppercase letters, 4 digits, 1 uppercase letter
- Regex: ^[A-Z]{5}[0-9]{4}[A-Z]{1}$
- Person must be at least 18 years old

IF DOCUMENT TYPE = PASSPORT (INDIA):
- Format: 1 uppercase letter followed by 7 digits
- Regex: ^[A-Z][0-9]{7}$
- Person must be at least 18 years old

IF DOCUMENT TYPE = MARKSHEET:
- SL NO / Roll Number must be alphanumeric
- Length between 5 to 15 characters 

IF DOCUMENT TYPE = EXPERIENCE LETTER:
- Employee ID / Roll No/Staff No/ID must be alphanumeric
- Length between 5 to 15 characters
- Name can contain Mr., Ms., Dr.,Mrs. prefixes and suffixes like Jr., Sr.

----------------------------------
DECISION RULES:

- If ANY required field is missing → valid = false
- If ANY format rule fails → valid = false
- If DOB invalid → valid = false
- DO NOT infer missing values
- DO NOT hallucinate values

----------------------------------
RESPONSE FORMAT (STRICT JSON ARRAY ONLY):

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

public String ocrCleanupPrompt(String rawOcrText){
        return """
                You are a STRICT OCR post-processing engine.
                
                The following text is RAW OCR output extracted from a document.
                The text may contain:
                - broken words
                - random symbols
                - incorrect spacing
                - missing separators
                
                Your task:
                - Identify the document type
                - Extract ONLY the following fields if present
                - Normalize them into a clean, readable format
                
                RULES (VERY IMPORTANT):
                - Do NOT guess missing values
                - Do NOT invent data
                - If a field is not found, leave it empty
                - Remove noise and symbols
                - Keep original values exactly (no correction)
                
                FIELDS TO EXTRACT (based on document type):
                
                If PAN:
                - Name
                - PAN Number
                - Date of Birth (DOB)
                
                If Aadhaar:
                - Name
                - Date of Birth (DOB)
                - Aadhaar Number
                
                If Marksheet or Grade Card
                - Name
                - SL NO / Roll Number
                
                If Experience Letter / Employment Certificate
                - Name
                - Employee ID / Roll No/Staff No/ID
                - mail ID /e-mail/HR Contact (if present)
                - Company Name(if present)
                
                OUTPUT FORMAT (STRICT — NO MARKDOWN):
                
                {
                  "documentType": "<PAN | AADHAAR | MARKSHEET | EXPERIENCE LETTER | UNKNOWN>",
                  "extractedText": "Name: <value>\\nDOB: <value>\\nID: <value>\\nCompany: <value>\\nAuthority Contact: <value>"
                }
                
                ----------------------------------
                RAW OCR TEXT:
                %s
                ----------------------------------
                
                """.formatted(rawOcrText);
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
