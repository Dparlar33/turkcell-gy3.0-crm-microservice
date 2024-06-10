package com.turkcell.turkcellcrm.searchService.business.abstracts;

import com.turkcell.turkcellcrm.common.events.customer.CustomerCreatedEvent;
import com.turkcell.turkcellcrm.common.events.customer.CustomerUpdatedEvent;
import com.turkcell.turkcellcrm.searchService.business.dto.dynamics.DynamicQuery;
import com.turkcell.turkcellcrm.searchService.business.dto.request.GetAllCustomerRequest;
import com.turkcell.turkcellcrm.searchService.business.dto.response.GetAllCustomerResponse;

import java.util.List;

public interface SearchCustomerService {

    void add(CustomerCreatedEvent customerCreatedEvent);

    List<GetAllCustomerResponse> getAll(DynamicQuery dynamicQuery);

    void update(CustomerUpdatedEvent customerUpdatedEvent);

    void deleteCustomer(int id);
}
