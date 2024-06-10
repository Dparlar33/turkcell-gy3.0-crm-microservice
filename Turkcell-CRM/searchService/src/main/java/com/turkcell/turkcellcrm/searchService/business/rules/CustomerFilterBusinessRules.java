package com.turkcell.turkcellcrm.searchService.business.rules;

import com.turkcell.turkcellcrm.searchService.business.messages.CustomerFilterBusinessRulesMessages;
import com.turkcell.turkcellcrm.searchService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.turkcellcrm.searchService.dataAccess.SearchCustomerRepository;
import com.turkcell.turkcellcrm.searchService.entities.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomerFilterBusinessRules {

    private SearchCustomerRepository searchCustomerRepository;

    public Customer IsCustomerAlreadyDeleted(int customerId){

        Customer customer = this.searchCustomerRepository.findCustomersByCustomerId(customerId).
                orElseThrow(() -> new BusinessException(CustomerFilterBusinessRulesMessages.CUSTOMER_NOT_EXISTS));

        if(customer.getDeletedDate() != null){
            throw new BusinessException(CustomerFilterBusinessRulesMessages.CUSTOMER_IS_ALREADY_DELETED);
        }

        return customer;
    }
}
