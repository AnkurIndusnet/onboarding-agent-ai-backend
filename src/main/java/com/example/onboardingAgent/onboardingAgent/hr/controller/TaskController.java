package com.example.onboardingAgent.onboardingAgent.hr.controller;

import com.example.onboardingAgent.onboardingAgent.hr.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/hr/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping("/assign")
    public String assign(@RequestBody String payload) {
        return service.format(payload);
    }
}

