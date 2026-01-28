package com.example.onboardingAgent.onboardingAgent.employee.service.serviceImpl;

import com.example.onboardingAgent.onboardingAgent.ai.PromptTemplateService;
import com.example.onboardingAgent.onboardingAgent.employee.service.ChecklistService;
import com.example.onboardingAgent.onboardingAgent.hr.service.GeminiApiService;
import com.example.onboardingAgent.onboardingAgent.model.UserEntity;
import com.example.onboardingAgent.onboardingAgent.repository.ChecklistTaskRepository;
import com.example.onboardingAgent.onboardingAgent.repository.UserRepository;
import com.example.onboardingAgent.onboardingAgent.security.dto.response.ChecklistTaskResponseDTO;
import com.example.onboardingAgent.onboardingAgent.security.dto.response.GeminiChecklistItemDTO;
import com.example.onboardingAgent.onboardingAgent.security.service.JwtService;
import com.example.onboardingAgent.onboardingAgent.utility.ChecklistMapper;
import com.example.onboardingAgent.onboardingAgent.utility.GeminiJsonExtractor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<ChecklistTaskResponseDTO> generateChecklist(String role,String userEmail) {
        // 1. Prompt
        String prompt = prompts.checklistPrompt(role);

        // 2. Gemini call
        String rawResponse = gemini.generateText(prompt);

        // 3. Parse JSON
        List<GeminiChecklistItemDTO> items;
        try {
            String cleanJson = GeminiJsonExtractor.extractJsonArray(rawResponse);

            items = objectMapper.readValue(
                    cleanJson,
                    new TypeReference<>() {
                    }
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid checklist JSON from Gemini", e);
        }
        Optional<String> user= userRepository.findByEmail(userEmail).map(UserEntity::getEmpId);
        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }
        // 4. Map → Save → Map
        return items.stream()
                .map(dto -> checklistMapper.toEntity(dto, user.get()))
                .map(checklistTaskRepository::save)
                .map(checklistMapper::toResponse)
                .toList();
    }

}
