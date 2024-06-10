package com.turkcell.turkcellcrm.customerService.business.dtos.response.address;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatedAddressResponse {
    private int id;
    private String customerFirstName;
    private String street;
    private String flatNumber;
    private String addressDescription;
    private String cityName;
}
