package com.turkcell.turkcellcrm.searchService.business.rules;

import com.turkcell.turkcellcrm.searchService.business.messages.CustomerFilterBusinessRulesMessages;
import com.turkcell.turkcellcrm.searchService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.turkcellcrm.searchService.dataAccess.SearchCustomerRepository;
import com.turkcell.turkcellcrm.searchService.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerFilterBusinessRulesTest {

    @Mock
    private SearchCustomerRepository searchCustomerRepository;

    @InjectMocks
    private CustomerFilterBusinessRules customerFilterBusinessRules;

    @BeforeEach
    void setUp() {
        customerFilterBusinessRules = new CustomerFilterBusinessRules(searchCustomerRepository);
    }

    @Test
    void testIsCustomerAlreadyDeleted_CustomerExistsAndNotDeleted() {

        int customerId = 100;
        Customer customer = new Customer();

        when(searchCustomerRepository.findCustomersByCustomerId(customerId)).thenReturn(Optional.of(customer));

        Customer result = customerFilterBusinessRules.IsCustomerAlreadyDeleted(customerId);

        assertEquals(customer, result);

        verify(searchCustomerRepository).findCustomersByCustomerId(customerId);
    }

    @Test
    void testIsCustomerAlreadyDeleted_CustomerDoesNotExist() {

        int customerId = 200;

        when(searchCustomerRepository.findCustomersByCustomerId(customerId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            customerFilterBusinessRules.IsCustomerAlreadyDeleted(customerId);
        });

        assertEquals(CustomerFilterBusinessRulesMessages.CUSTOMER_NOT_EXISTS, exception.getMessage());

        verify(searchCustomerRepository).findCustomersByCustomerId(customerId);
    }

    @Test
    void testIsCustomerAlreadyDeleted_CustomerAlreadyDeleted() {

        int customerId = 300;
        Customer customer = new Customer();
        customer.setDeletedDate(LocalDateTime.now());

        when(searchCustomerRepository.findCustomersByCustomerId(customerId)).thenReturn(Optional.of(customer));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            customerFilterBusinessRules.IsCustomerAlreadyDeleted(customerId);
        });

        assertEquals(CustomerFilterBusinessRulesMessages.CUSTOMER_IS_ALREADY_DELETED, exception.getMessage());

        verify(searchCustomerRepository).findCustomersByCustomerId(customerId);
    }
}