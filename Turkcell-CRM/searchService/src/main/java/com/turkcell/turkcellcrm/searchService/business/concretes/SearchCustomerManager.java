package com.turkcell.turkcellcrm.searchService.business.concretes;

import com.turkcell.turkcellcrm.common.events.customer.CustomerCreatedEvent;
import com.turkcell.turkcellcrm.common.events.customer.CustomerUpdatedEvent;
import com.turkcell.turkcellcrm.searchService.business.abstracts.SearchCustomerService;
import com.turkcell.turkcellcrm.searchService.business.abstracts.SearchService;
import com.turkcell.turkcellcrm.searchService.business.dto.dynamics.DynamicQuery;
import com.turkcell.turkcellcrm.searchService.business.dto.request.GetAllCustomerRequest;
import com.turkcell.turkcellcrm.searchService.business.dto.response.GetAllCustomerResponse;
import com.turkcell.turkcellcrm.searchService.business.rules.CustomerFilterBusinessRules;
import com.turkcell.turkcellcrm.searchService.core.utilities.mapping.ModelMapperService;
import com.turkcell.turkcellcrm.searchService.entities.Customer;
import com.turkcell.turkcellcrm.searchService.dataAccess.SearchCustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchCustomerManager implements SearchCustomerService {

    private SearchCustomerRepository searchCustomerRepository;
    private ModelMapperService modelMapperService;
    private CustomerFilterBusinessRules customerFilterBusinessRules;
    private SearchService searchService;

    @Override
    public void add(CustomerCreatedEvent customerCreatedEvent) {

        Customer customer = this.modelMapperService.forRequest().map(customerCreatedEvent, Customer.class);
        customer.setId(null);

        this.searchCustomerRepository.save(customer);
    }

    @Override
    public List<GetAllCustomerResponse> getAll(DynamicQuery dynamicQuery) {

        List<Customer> customerList = this.searchService.dynamicSearch(dynamicQuery,Customer.class);

        return customerList.stream().
                map(customer -> this.modelMapperService.forResponse().
                map(customer, GetAllCustomerResponse.class)).toList();
    }

    @Override
    public void update(CustomerUpdatedEvent customerUpdatedEvent) {

        Customer customer = this.modelMapperService.forRequest().map(customerUpdatedEvent, Customer.class);

        this.searchCustomerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(int id) {

        Customer customer = this.customerFilterBusinessRules.IsCustomerAlreadyDeleted(id);
        customer.setDeletedDate(LocalDateTime.now());

        this.searchCustomerRepository.save(customer);
    }
}
