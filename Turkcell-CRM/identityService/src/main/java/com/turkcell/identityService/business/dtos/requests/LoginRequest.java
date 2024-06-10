package com.turkcell.identityService.business.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotNull(message = "This field is required")
    @Email(message = "Please enter a valid e-mail address")
    private String email;

    @NotNull(message = "This field is required")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-={}|:;\"'<>,.?/~`])(?=.*\\d).{8,}$",
            message = "Password must contain at least one uppercase letter, one special character, one digit, and be at least 8 characters long")
    private String password;
}