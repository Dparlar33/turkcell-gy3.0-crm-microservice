package com.turkcell.turkcellcrm.searchService.business.abstracts;

import com.turkcell.turkcellcrm.common.events.product.ProductCreatedEvent;
import com.turkcell.turkcellcrm.common.events.product.ProductUpdatedEvent;
import com.turkcell.turkcellcrm.searchService.business.dto.dynamics.DynamicQuery;
import com.turkcell.turkcellcrm.searchService.business.dto.request.SelectProductRequest;
import com.turkcell.turkcellcrm.searchService.business.dto.response.GetAllProductResponse;

import java.util.List;

public interface SearchProductService {

    void add(ProductCreatedEvent productCreatedEvent);

    List<GetAllProductResponse> getAll(DynamicQuery dynamicQuery);

    void update(ProductUpdatedEvent productUpdatedEvent);

    void delete (int productId);

    void select(SelectProductRequest selectProductRequests);
}
