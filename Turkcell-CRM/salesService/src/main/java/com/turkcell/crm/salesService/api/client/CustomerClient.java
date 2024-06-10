package com.turkcell.crm.salesService.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customerService", url = "http://localhost:10043/customerservice/api/v1/customers/individualcustomers")
public interface CustomerClient {
    @GetMapping("/getAddressByCustomerId/{customerId}")
    int getAddressIdByCustomerId(@PathVariable int customerId);
}
