package com.turkcell.turkcellcrm.customerService.business.abstracts;

import com.turkcell.turkcellcrm.customerService.business.dtos.request.address.CreateAddressRequest;
import com.turkcell.turkcellcrm.customerService.business.dtos.request.address.UpdateAddressRequest;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.address.CreatedAddressResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.address.GetAllAddressResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.address.GetByIdAddressResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.address.UpdatedAddressResponse;

import java.util.List;

public interface AddressService {
    CreatedAddressResponse add(CreateAddressRequest createAddressRequest);
    List<GetAllAddressResponse> getAll();
    GetByIdAddressResponse getById(int id);
    UpdatedAddressResponse update(UpdateAddressRequest updateAddressRequest);
    void delete(int id);
}
