package com.example.onboardingAgent.onboardingAgent.employee.service.serviceImpl;

import com.example.onboardingAgent.onboardingAgent.ai.PromptTemplateService;
import com.example.onboardingAgent.onboardingAgent.employee.dto.request.TaskSubmitRequestDTO;
import com.example.onboardingAgent.onboardingAgent.employee.service.ChecklistService;
import com.example.onboardingAgent.onboardingAgent.enums.MasterTaskStatus;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public void submitTask(TaskSubmitRequestDTO request, String userEmail) {

        ChecklistTaskEntity task = checklistTaskRepository.findById(request.getTaskId())
                .orElseThrow(() ->
                        new RuntimeException("Task not found"));

        List<ChecklistTaskFieldEntity> fields =
                checklistTaskFieldRepository1.findByTask_TaskId(task.getTaskId());

        if (fields.isEmpty()) {
            throw new RuntimeException("No fields found for task");
        }

        Map<Long, String> submittedMap =
                request.getValues().stream()
                        .collect(Collectors.toMap(
                                TaskSubmitRequestDTO.FieldValueDTO::getFieldId,
                                TaskSubmitRequestDTO.FieldValueDTO::getValue
                        ));

        for (ChecklistTaskFieldEntity field : fields) {

//            if (!submittedMap.containsKey(field.getFieldId())) {
//                throw new RuntimeException(
//                        "Missing value for fieldId: " + field.getFieldId()
//                );
//            }

            String value = submittedMap.get(field.getFieldId());

            if (Boolean.TRUE.equals(field.getRequired())
                    && (value == null || value.isBlank())) {
                throw new RuntimeException(
                        "Required field empty: " + field.getLabel()
                );
            }

            field.setValue(value);
            field.setStatus(MasterTaskStatus.VERIFICATION_REQUIRED);
        }

        checklistTaskFieldRepository1.saveAll(fields);

        task.setSubmissionDateTime(LocalDateTime.now());
        task.setStatus(MasterTaskStatus.VERIFICATION_REQUIRED);
        checklistTaskRepository.save(task);
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        int remainingTasks =
                checklistTaskRepository.countByUserIdAndStatusNot(
                        user.getEmpId(),
                        MasterTaskStatus.VERIFICATION_REQUIRED
                );

        if (remainingTasks == 0) {
            user.setDocumentRequired(false);
            user.setVerificationRequired(true);
            userRepository.save(user);
        }
    }

}
