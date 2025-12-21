package com.example.onboardingAgent.onboardingAgent.hr.controller;

import com.example.onboardingAgent.onboardingAgent.hr.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


    @RestController
    @RequestMapping("/hr/announcement")
    @RequiredArgsConstructor
    public class AnnouncementController {

        private final AnnouncementService service;

        @PostMapping("/send")
        public String send(@RequestBody String payload) {
            return service.format(payload);
        }
    }


