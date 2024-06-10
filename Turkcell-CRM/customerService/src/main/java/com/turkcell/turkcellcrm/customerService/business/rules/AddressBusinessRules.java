package com.turkcell.turkcellcrm.customerService.business.rules;

import com.turkcell.turkcellcrm.customerService.business.dtos.request.address.CreateAddressRequest;
import com.turkcell.turkcellcrm.customerService.business.messages.AddressMessages;
import com.turkcell.turkcellcrm.customerService.core.business.abstracts.MessageService;
import com.turkcell.turkcellcrm.customerService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.turkcellcrm.customerService.dataAccess.AddressRepository;
import com.turkcell.turkcellcrm.customerService.entity.Address;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class AddressBusinessRules {

    private AddressRepository addressRepository;
    private MessageService messageService;

    public Address isCustomerExist(CreateAddressRequest createAddressRequest){

        Optional<Address> address =this.addressRepository.findByCustomerId(createAddressRequest.getCustomerId());

        if (address.isPresent()){
            throw new BusinessException(messageService.getMessage(AddressMessages.ADDRESS_CUSTOMER_DOES_NOT_EXIST));
        }
        return address.get();
    }

    public Address isAddressExistByID(int id){

        Optional<Address> address = this.addressRepository.findAddressById(id);

        if (address.isEmpty()){
            throw new BusinessException(messageService.getMessage(AddressMessages.ADDRESS_ID_DOES_NOT_EXIST));
        }
        return address.get();
    }

    public Address isAddressAlreadyDeleted(int id){

        Address address = this.isAddressExistByID(id);

        if (address.getDeletedDate() != null){
            throw new BusinessException(messageService.getMessage(AddressMessages.ADDRESS_DOES_NOT_EXIST));
        }
        return address;
    }
}
