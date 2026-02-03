package com.example.onboardingAgent.onboardingAgent.employee.service.serviceImpl;

import com.example.onboardingAgent.onboardingAgent.ai.PromptTemplateService;
import com.example.onboardingAgent.onboardingAgent.employee.service.ChecklistService;
import com.example.onboardingAgent.onboardingAgent.hr.service.GeminiApiService;
import com.example.onboardingAgent.onboardingAgent.model.ChecklistTaskEntity;
import com.example.onboardingAgent.onboardingAgent.model.ChecklistTaskFieldEntity;
import com.example.onboardingAgent.onboardingAgent.model.UserEntity;
import com.example.onboardingAgent.onboardingAgent.repository.ChecklistTaskFieldRepository;
import com.example.onboardingAgent.onboardingAgent.repository.ChecklistTaskRepository;
import com.example.onboardingAgent.onboardingAgent.repository.UserRepository;
import com.example.onboardingAgent.onboardingAgent.security.dto.response.ChecklistTaskFieldResponseDTO;
import com.example.onboardingAgent.onboardingAgent.security.dto.response.ChecklistTaskResponseDTO;
import com.example.onboardingAgent.onboardingAgent.security.dto.response.GeminiChecklistItemDTO;
import com.example.onboardingAgent.onboardingAgent.security.service.JwtService;
import com.example.onboardingAgent.onboardingAgent.utility.ChecklistMapper;
import com.example.onboardingAgent.onboardingAgent.utility.ChecklistTaskFieldMapper;
import com.example.onboardingAgent.onboardingAgent.utility.GeminiJsonExtractor;
import com.example.onboardingAgent.onboardingAgent.utility.TaskFieldFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistServiceImpl implements ChecklistService {

    private final GeminiApiService gemini;
    private final PromptTemplateService prompts;
    private final ChecklistTaskRepository checklistTaskRepository;
    private final ChecklistMapper checklistMapper;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ChecklistTaskFieldRepository checklistTaskFieldRepository1;
    private final ChecklistTaskFieldMapper checklistTaskFieldMapper;



    // 🔥 NEW
    private final ChecklistTaskFieldRepository checklistTaskFieldRepository;
    private final TaskFieldFactory taskFieldFactory;

    @Override
    public List<ChecklistTaskResponseDTO> generateChecklist(
            String role,
            String userEmail
    ) {

        // 1. Prompt
        String prompt = prompts.checklistPrompt(role);

        // 2. Gemini call
        String rawResponse = gemini.generateText(prompt);

        // 3. Parse Gemini JSON safely
        List<GeminiChecklistItemDTO> items;
        try {
            String cleanJson =
                    GeminiJsonExtractor.extractJsonArray(rawResponse);

            items = objectMapper.readValue(
                    cleanJson,
                    new TypeReference<>() {}
            );
        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid checklist JSON from Gemini", e);
        }

        // 4. Resolve user
        String empId = userRepository.findByEmail(userEmail)
                .map(UserEntity::getEmpId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 5. Save parent + child together
        return items.stream()
                .map(dto -> {


                    var taskEntity =
                            checklistMapper.toEntity(dto, empId);

                    ChecklistTaskEntity savedTask =
                            checklistTaskRepository.save(taskEntity);


                    List<ChecklistTaskFieldEntity> fields =
                            taskFieldFactory.buildFields(savedTask);

                    checklistTaskFieldRepository.saveAll(fields);


                    return checklistMapper.toResponse(savedTask);
                })
                .toList();
    }

    @Override
    public List<ChecklistTaskFieldResponseDTO> getFieldsByTaskId(
            Long taskId
    ) {
        return checklistTaskFieldRepository1.findByTask_TaskId(taskId).stream()
                .map(checklistTaskFieldMapper::toResponse)
                .toList();
    }

    @Override
    public List<ChecklistTaskResponseDTO> fetchChecklist(String userEmail) {

        // Resolve user
        String empId = userRepository.findByEmail(userEmail)
                .map(UserEntity::getEmpId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch from DB
        List<ChecklistTaskEntity> tasks =
                checklistTaskRepository.findByUserIdOrderByAskDateTimeAsc(empId);

        // If none exist → empty list (as required)
        if (tasks.isEmpty()) {
            return List.of();
        }

        // Map to SAME response as generateChecklist
        return tasks.stream()
                .map(checklistMapper::toResponse)
                .toList();
    }

}
