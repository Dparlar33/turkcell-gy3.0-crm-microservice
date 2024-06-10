package com.turkcell.crm.accountService.business.dtos.request.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderAccountResponse {
    private int accountId;
    private int orderId;
}
