package com.example.onboardingAgent.onboardingAgent.security.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {

    private String name;
    private String role;
    private String empId;
    private int pendingTask;
    private int completedTask;
    private String location;
    private String designation;
}
