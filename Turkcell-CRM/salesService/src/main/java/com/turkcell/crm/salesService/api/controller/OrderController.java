package com.turkcell.crm.salesService.api.controller;

import com.turkcell.crm.salesService.business.abstracts.OrderService;
import com.turkcell.crm.salesService.business.dto.response.GetAllOrderResponse;
import com.turkcell.crm.salesService.business.dto.response.GetByIdOrderResponse;
import com.turkcell.turkcellcrm.common.events.basket.CreateOrderRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salesservice/api/v1/orders/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping("/add")
    public void add(@RequestBody CreateOrderRequest createOrderRequest){
        this.orderService.add(createOrderRequest);
    }

    @GetMapping("/getOrders")
    public List<GetAllOrderResponse>  getAll(){
        return this.orderService.getAll();
    }

    @GetMapping("/getById/{orderId}")
    public GetByIdOrderResponse getById(@PathVariable String orderId){
        return this.orderService.getById(orderId);
    }
}
