package com.turkcell.crm.salesService.business.abstracts;


import com.turkcell.crm.salesService.business.dto.response.GetAllOrderResponse;
import com.turkcell.crm.salesService.business.dto.response.GetByIdOrderResponse;
import com.turkcell.turkcellcrm.common.events.basket.CreateOrderRequest;

import java.util.List;

public interface OrderService {
    void add(CreateOrderRequest createOrderRequest);
    List<GetAllOrderResponse>  getAll();
    GetByIdOrderResponse getById(String orderId);
}
