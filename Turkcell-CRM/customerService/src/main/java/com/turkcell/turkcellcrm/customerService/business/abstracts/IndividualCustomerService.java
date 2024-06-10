package com.turkcell.turkcellcrm.customerService.business.abstracts;

import com.turkcell.turkcellcrm.customerService.business.dtos.request.customer.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.turkcellcrm.customerService.business.dtos.request.customer.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.customer.individualCustomer.CreatedIndividualCustomerResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.customer.individualCustomer.GetAllIndividualCustomerResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.customer.individualCustomer.GetByIdIndividualCustomerResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.customer.individualCustomer.UpdatedIndividualCustomerResponse;

import java.util.List;

public interface IndividualCustomerService {
    CreatedIndividualCustomerResponse add(CreateIndividualCustomerRequest createIndividualCustomerRequest);
    List<GetAllIndividualCustomerResponse> getAll();
    GetByIdIndividualCustomerResponse getById(int id);
    UpdatedIndividualCustomerResponse update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest);
    void delete(int id);
    int getAddresIdByCustomerId(int customerId);
}
