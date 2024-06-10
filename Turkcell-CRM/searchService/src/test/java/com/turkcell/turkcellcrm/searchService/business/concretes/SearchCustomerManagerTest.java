package com.turkcell.turkcellcrm.searchService.business.concretes;

import com.turkcell.turkcellcrm.common.events.customer.CustomerCreatedEvent;
import com.turkcell.turkcellcrm.common.events.customer.CustomerUpdatedEvent;
import com.turkcell.turkcellcrm.searchService.business.abstracts.SearchService;
import com.turkcell.turkcellcrm.searchService.business.dto.dynamics.DynamicFilter;
import com.turkcell.turkcellcrm.searchService.business.dto.dynamics.DynamicQuery;
import com.turkcell.turkcellcrm.searchService.business.dto.dynamics.DynamicSort;
import com.turkcell.turkcellcrm.searchService.business.dto.response.GetAllCustomerResponse;
import com.turkcell.turkcellcrm.searchService.business.dto.response.GetAllProductResponse;
import com.turkcell.turkcellcrm.searchService.business.rules.CustomerFilterBusinessRules;
import com.turkcell.turkcellcrm.searchService.core.utilities.mapping.ModelMapperService;
import com.turkcell.turkcellcrm.searchService.dataAccess.SearchCustomerRepository;
import com.turkcell.turkcellcrm.searchService.entities.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchCustomerManagerTest {
    @Mock
    CustomerFilterBusinessRules customerFilterBusinessRules;
    @Mock
    ModelMapperService modelMapperService;
    @Mock
    ModelMapper modelMapper;
    @Mock
    SearchCustomerRepository searchCustomerRepository;
    @Mock
    SearchService searchService;
    @InjectMocks
    SearchCustomerManager searchCustomerManager;

    @Test
    void testAdd() {

        CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent();
        Customer customer = new Customer();

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(customerCreatedEvent,Customer.class)).thenReturn(customer);

        searchCustomerManager.add(customerCreatedEvent);

        verify(modelMapperService.forRequest()).map(customerCreatedEvent,Customer.class);
        verify(searchCustomerRepository).save(customer);
        assertDoesNotThrow(()->searchCustomerManager.add(customerCreatedEvent));
    }

    @Test
    void testGetAll() {

        List<DynamicFilter> filters = Collections.emptyList();
        List<DynamicSort> sorts = Collections.emptyList();
        DynamicQuery dynamicQuery = new DynamicQuery(filters, sorts);

        GetAllCustomerResponse response1 = new GetAllCustomerResponse();
        GetAllCustomerResponse response2 = new GetAllCustomerResponse();

        Customer customer1 = new Customer();
        Customer customer2 = new Customer();

        List<Customer> customerList = Arrays.asList(customer1, customer2);

        when(searchService.dynamicSearch(dynamicQuery, Customer.class)).thenReturn(customerList);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(customer1, GetAllCustomerResponse.class)).thenReturn(response1);
        when(modelMapper.map(customer2, GetAllCustomerResponse.class)).thenReturn(response2);

        List<GetAllCustomerResponse> responses = searchCustomerManager.getAll(dynamicQuery);

        assertEquals(2, responses.size());
        assertTrue(responses.contains(response1));
        assertTrue(responses.contains(response2));

        verify(searchService).dynamicSearch(dynamicQuery, Customer.class);
        verify(modelMapperService.forResponse(), times(2)).map(any(), eq(GetAllCustomerResponse.class));
    }

    @Test
    void testUpdate() {

        CustomerUpdatedEvent customerUpdatedEvent = new CustomerUpdatedEvent();
        Customer customer = new Customer();

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(customerUpdatedEvent,Customer.class)).thenReturn(customer);

        searchCustomerManager.update(customerUpdatedEvent);

        verify(modelMapperService.forRequest()).map(customerUpdatedEvent,Customer.class);
        verify(searchCustomerRepository).save(customer);

        assertDoesNotThrow(()->searchCustomerManager.update(customerUpdatedEvent));
    }

    @Test
    void testDeleteCustomer() {

        int customerId = 111;
        Customer customer = new Customer();

        when(customerFilterBusinessRules.IsCustomerAlreadyDeleted(customerId)).thenReturn(customer);

        searchCustomerManager.deleteCustomer(111);

        verify(customerFilterBusinessRules).IsCustomerAlreadyDeleted(customerId);
        verify(searchCustomerRepository).save(customer);
    }
}