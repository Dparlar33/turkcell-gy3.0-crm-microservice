package com.turkcell.identityService.business.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotNull(message = "This field is required")
    @Email(message = "Please enter a valid e-mail address")
    private String email;

    @NotNull(message = "This field is required")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-={}|:;\"'<>,.?/~`])(?=.*\\d).{8,}$",
            message = "Password must contain at least one uppercase letter, one special character, one digit, and be at least 8 characters long")
    private String password;

    @NotNull(message = "This field is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotNull(message = "This field is required")
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")
    private String lastName;
}
