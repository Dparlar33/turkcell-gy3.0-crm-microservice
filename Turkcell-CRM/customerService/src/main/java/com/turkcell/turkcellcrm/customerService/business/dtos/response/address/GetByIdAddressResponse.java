package com.turkcell.turkcellcrm.customerService.business.dtos.response.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdAddressResponse {
    private int id;
    private String customerFirstName;
    private String cityName;
    private String street;
    private String flatNumber;
    private String addressDescription;
}
