package com.turkcell.crm.accountService.business.dtos.request.account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateAccountRequest {

    @NotNull
    @Size(min = 19,max = 19)
    @Pattern(regexp = "\\d+", message = "Account number must contain only digits")
    private String accountNumber;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Account name must contain only letters")
    private String accountName;

    @NotNull
    private int accountTypeId;

    @NotNull
    private int customerId;
}
