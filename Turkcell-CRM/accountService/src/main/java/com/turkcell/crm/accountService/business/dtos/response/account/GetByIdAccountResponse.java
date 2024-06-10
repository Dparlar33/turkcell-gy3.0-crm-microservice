package com.turkcell.crm.accountService.business.dtos.response.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdAccountResponse {
    private int id;
    private String accountNumber;
    private String accountName;
    private int accountTypeId;
    private int customerId;
}
