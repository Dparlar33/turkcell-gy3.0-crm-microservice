package com.turkcell.turkcellcrm.searchService.api.client;


import com.turkcell.turkcellcrm.common.events.basket.CreateBasketItemRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "basketService", url = "http://localhost:10041/basketservice/api/v1/baskets/basket")
public interface BasketClient {
    @PostMapping("/addItems")
    void addItem(@RequestBody CreateBasketItemRequest createBasketItemRequests);
}






