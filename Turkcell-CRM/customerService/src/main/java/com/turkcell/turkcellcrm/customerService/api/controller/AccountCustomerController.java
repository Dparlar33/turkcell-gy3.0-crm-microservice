package com.turkcell.turkcellcrm.customerService.api.controller;

import com.turkcell.turkcellcrm.customerService.business.rules.IndividualCustomerBusinessRules;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customerservice/api/v1/customers/account")
@AllArgsConstructor
public class AccountCustomerController {
    private IndividualCustomerBusinessRules individualCustomerBusinessRules;

    @GetMapping("/get/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean getAll(@PathVariable int customerId){
       return  this.individualCustomerBusinessRules.isCustomerIdExistForAccount
               (customerId);
    }
}
