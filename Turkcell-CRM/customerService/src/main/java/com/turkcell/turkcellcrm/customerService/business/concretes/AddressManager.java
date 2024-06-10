package com.turkcell.turkcellcrm.customerService.business.concretes;

import com.turkcell.turkcellcrm.customerService.business.abstracts.AddressService;
import com.turkcell.turkcellcrm.customerService.business.dtos.request.address.CreateAddressRequest;
import com.turkcell.turkcellcrm.customerService.business.dtos.request.address.UpdateAddressRequest;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.address.CreatedAddressResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.address.GetAllAddressResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.address.GetByIdAddressResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.address.UpdatedAddressResponse;
import com.turkcell.turkcellcrm.customerService.business.rules.AddressBusinessRules;
import com.turkcell.turkcellcrm.customerService.core.utilities.mapping.ModelMapperService;
import com.turkcell.turkcellcrm.customerService.dataAccess.AddressRepository;
import com.turkcell.turkcellcrm.customerService.entity.Address;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressManager implements AddressService {

    private AddressRepository addressRepository;
    private ModelMapperService modelMapperService;
    private AddressBusinessRules addressBusinessRules;

    @Override
    public CreatedAddressResponse add(CreateAddressRequest createAddressRequest) {

        this.addressBusinessRules.isCustomerExist(createAddressRequest);

        Address address = this.modelMapperService.forRequest().map(createAddressRequest, Address.class);

        CreatedAddressResponse createdAddressResponse = this.modelMapperService.forResponse().map(this.addressRepository.save(address), CreatedAddressResponse.class);
        return createdAddressResponse;
    }

    @Override
    public List<GetAllAddressResponse> getAll() {

        List<Address> addresses = this.addressRepository.findByDeletedDateIsNull();

        List<GetAllAddressResponse> getAllAddressResponses = addresses.stream().map(address -> this.modelMapperService.forResponse().
                map(address, GetAllAddressResponse.class)).toList();
        return getAllAddressResponses;
    }

    @Override
    public GetByIdAddressResponse getById(int id) {

        this.addressBusinessRules.isAddressAlreadyDeleted(id);
        Optional<Address> address =this.addressRepository.findById(id);

        return this.modelMapperService.forResponse().map(address.get(), GetByIdAddressResponse.class);
    }

    @Override
    public UpdatedAddressResponse update(UpdateAddressRequest updateAddressRequest) {

        this.addressBusinessRules.isAddressAlreadyDeleted(updateAddressRequest.getId());
        Address address = this.modelMapperService.forRequest().map(updateAddressRequest,Address.class);

        return this.modelMapperService.forResponse().
                map(this.addressRepository.save(address), UpdatedAddressResponse.class);
    }

    @Override

    public void delete(int id) {

        Address address =this.addressBusinessRules.isAddressAlreadyDeleted(id);
        address.setDeletedDate(LocalDateTime.now());

        this.addressRepository.save(address);
    }
}
