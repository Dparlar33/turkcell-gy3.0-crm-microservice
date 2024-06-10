package com.turkcell.turkcellcrm.searchService.business.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GetAllCustomerResponse {

    private int customerId;
    private String nationalityNumber;
    private String firstName;
    private String middleName;
    private String lastName;

}
