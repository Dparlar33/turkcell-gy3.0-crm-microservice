package com.turkcell.crm.salesService.api.client;

import com.turkcell.crm.salesService.business.dto.response.OrderAccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "accountService", url = "http://localhost:10051/accountservice/api/v1/accounts")
public interface AccountClient {
    @PostMapping("/setStatus")
    void setAccountStatusByOrderId(@RequestBody OrderAccountResponse orderAccountResponse);
}
