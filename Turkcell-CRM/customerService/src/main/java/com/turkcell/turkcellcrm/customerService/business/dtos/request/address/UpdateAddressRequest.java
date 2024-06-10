package com.turkcell.turkcellcrm.customerService.business.dtos.request.address;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAddressRequest {

    @NotNull
    private int id;

    @NotNull
    @Size(min = 2 ,max = 30)
    private String street;

    @NotNull
    @Size(min = 2 ,max = 30)
    private String flatNumber;

    @NotNull
    @Size(min = 2 ,max = 100)
    private String addressDescription;

    @NotNull
    private int cityId;

    @NotNull
    private int customerId;
}
