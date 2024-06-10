package com.turkcell.crm.accountService.business.dtos.request.accountType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAccountTypeRequest {
    @NotNull
    @Size(min = 7, max = 50)
    private String accountTypeName;
}
