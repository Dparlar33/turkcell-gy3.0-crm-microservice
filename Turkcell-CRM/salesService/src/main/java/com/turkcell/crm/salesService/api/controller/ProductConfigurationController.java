package com.turkcell.crm.salesService.api.controller;

import com.turkcell.crm.salesService.business.abstracts.ProductConfigurationService;
import com.turkcell.crm.salesService.business.dto.response.GetAllProductConfigResponse;
import com.turkcell.turkcellcrm.common.events.basket.CreateOrderRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/salesservice/api/v1/orders/productconfiguration")
@AllArgsConstructor
public class ProductConfigurationController {

    private ProductConfigurationService productConfigurationService;

    @PostMapping("/getProductConfig")
    public List<GetAllProductConfigResponse> getAllProductConfig(@RequestBody CreateOrderRequest createOrderRequest){
        return this.productConfigurationService.getAllProductConfig(createOrderRequest);
    }

}
