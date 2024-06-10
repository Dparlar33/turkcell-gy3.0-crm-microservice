package com.turkcell.crm.salesService.business.abstracts;

import com.turkcell.crm.salesService.business.dto.response.GetAllProductConfigResponse;
import com.turkcell.turkcellcrm.common.events.basket.CreateOrderRequest;

import java.util.List;

public interface ProductConfigurationService {
    List<GetAllProductConfigResponse> getAllProductConfig(CreateOrderRequest createOrderRequest );
}
