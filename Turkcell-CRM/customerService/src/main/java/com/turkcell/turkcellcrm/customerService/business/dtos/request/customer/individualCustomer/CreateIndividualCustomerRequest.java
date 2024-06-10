package com.turkcell.turkcellcrm.customerService.business.dtos.request.customer.individualCustomer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomerRequest {

    @NotNull(message = "This field is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
    @Size(min = 2, max = 50)
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Middle name must contain only letters")
    @Size(max = 50)
    private String middleName;

    @NotNull(message = "This field is required")
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")
    private String lastName;

    @NotNull(message = "This field is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull(message = "This field is required")
    private int gender;

    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Father name must contain only letters")
    private String fatherName;

    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Mother name must contain only letters")
    private String motherName;

    @NotNull(message = "This field is required")
    @Size(min = 11, max = 11, message = "Nationality ID must be 11.")
    @Pattern(regexp = "\\d+", message = "Nationality ID must contain only digits")
    private String nationalityNumber;

    @NotNull(message = "This field is required")
    @Size(min = 10, max = 30)
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "This field is required")
    @Size(min = 10, max = 10)
    @Pattern(regexp = "\\d+", message = "Mobile Phone number must contain only digits")
    private String mobilePhoneNumber;
}
