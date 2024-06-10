package com.turkcell.crm.accountService.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customerService", url = "http://localhost:9050/customerservice/api/v1/customers/account")
public interface CustomerClient {
    @GetMapping("/get/{customerId}")
    public boolean getCustomer(@PathVariable  int customerId);
}
