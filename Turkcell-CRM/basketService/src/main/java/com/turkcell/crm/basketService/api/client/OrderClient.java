package com.turkcell.crm.basketService.api.client;


import com.turkcell.turkcellcrm.common.events.basket.CreateOrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "salesService", url = "http://localhost:10035/salesservice/api/v1/orders/order")
public interface OrderClient {
    @PostMapping("/add")
    void add(@RequestBody CreateOrderRequest createOrderRequest);
}
