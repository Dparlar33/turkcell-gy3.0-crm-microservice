package com.turkcell.crm.accountService.business.dtos.response.accountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreatedAccountTypeResponse {
    private int id;
    private String accountTypeName;
}
