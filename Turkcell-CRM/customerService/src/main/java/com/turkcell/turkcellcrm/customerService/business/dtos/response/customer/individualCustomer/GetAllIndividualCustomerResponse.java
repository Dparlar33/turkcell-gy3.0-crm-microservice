package com.turkcell.turkcellcrm.customerService.business.dtos.response.customer.individualCustomer;

import com.turkcell.turkcellcrm.customerService.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllIndividualCustomerResponse {
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String fatherName;
    private String motherName;
    private String nationalityId;
    private String email;
    private String mobilePhoneNumber;
}
