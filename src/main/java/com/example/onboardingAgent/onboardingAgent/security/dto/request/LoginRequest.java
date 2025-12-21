// java
package com.example.onboardingAgent.onboardingAgent.security.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LoginRequest {
    @NotBlank(message = "Email cannot be null or blank")
    @Email(message = "Invalid email format")
    private String email;
}
