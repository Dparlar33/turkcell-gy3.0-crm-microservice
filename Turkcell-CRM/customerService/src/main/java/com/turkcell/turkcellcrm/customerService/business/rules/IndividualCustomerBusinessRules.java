package com.turkcell.turkcellcrm.customerService.business.rules;

import com.turkcell.turkcellcrm.customerService.business.dtos.request.customer.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.turkcellcrm.customerService.business.dtos.request.customer.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.turkcellcrm.customerService.business.messages.IndividualCustomerMessages;
import com.turkcell.turkcellcrm.customerService.core.business.abstracts.MessageService;
import com.turkcell.turkcellcrm.customerService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.turkcellcrm.customerService.dataAccess.IndividualCustomerRepository;
import com.turkcell.turkcellcrm.customerService.entity.IndividualCustomer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class IndividualCustomerBusinessRules {

    private IndividualCustomerRepository individualCustomerRepository;
    private MessageService messageService;

    public void nationalityNumberCanNotBeDuplicate(CreateIndividualCustomerRequest createIndividualCustomerRequest){

        Optional<IndividualCustomer> individualCustomer = this.individualCustomerRepository.
                findIndividualCustomerByNationalityNumberEquals(createIndividualCustomerRequest.getNationalityNumber());

        if (individualCustomer.isPresent()){
            throw new BusinessException(messageService.getMessage(IndividualCustomerMessages.NATIONALITY_ID_CAN_NOT_DUPLICATE));
        }
    }
    public void nationalityNumberCanNotBeDuplicate(UpdateIndividualCustomerRequest updateIndividualCustomerRequest){

        Optional<IndividualCustomer> individualCustomer = this.individualCustomerRepository.
                findIndividualCustomerByNationalityNumberEquals(updateIndividualCustomerRequest.getNationalityNumber());

        if (individualCustomer.isPresent()){
            throw new BusinessException(messageService.getMessage(IndividualCustomerMessages.NATIONALITY_ID_CAN_NOT_DUPLICATE));
        }
    }

    public IndividualCustomer isCustomerIdExist(int id){

        Optional<IndividualCustomer> individualCustomer = this.individualCustomerRepository.findById(id);

        if (individualCustomer.isEmpty()){
            throw new BusinessException(messageService.getMessage(IndividualCustomerMessages.CUSTOMER_DOES_NOT_EXIST));
        }

        return individualCustomer.get();
    }
    public boolean isCustomerIdExistForAccount(int id){

        Optional<IndividualCustomer> individualCustomer = this.individualCustomerRepository.findById(id);

        return individualCustomer.isPresent();
    }

    public IndividualCustomer isCustomerAlreadyDeleted(int id){

        IndividualCustomer individualCustomer = this.isCustomerIdExist(id);

        if(individualCustomer.getDeletedDate() != null) {
            throw new BusinessException(messageService.getMessage(IndividualCustomerMessages.CUSTOMER_ALREADY_DELETED));
        }

        return individualCustomer;
    }
}
