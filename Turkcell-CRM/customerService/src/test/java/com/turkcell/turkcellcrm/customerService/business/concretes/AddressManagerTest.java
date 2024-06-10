package com.turkcell.turkcellcrm.customerService.business.concretes;

import com.turkcell.turkcellcrm.customerService.business.dtos.request.address.CreateAddressRequest;
import com.turkcell.turkcellcrm.customerService.business.dtos.request.address.UpdateAddressRequest;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.address.CreatedAddressResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.address.GetAllAddressResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.address.GetByIdAddressResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.address.UpdatedAddressResponse;
import com.turkcell.turkcellcrm.customerService.business.messages.AddressMessages;
import com.turkcell.turkcellcrm.customerService.business.messages.IndividualCustomerMessages;
import com.turkcell.turkcellcrm.customerService.business.rules.AddressBusinessRules;
import com.turkcell.turkcellcrm.customerService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.turkcellcrm.customerService.core.utilities.mapping.ModelMapperService;
import com.turkcell.turkcellcrm.customerService.dataAccess.AddressRepository;
import com.turkcell.turkcellcrm.customerService.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressManagerTest {
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private ModelMapperService modelMapperService;
    @Mock
    private AddressBusinessRules addressBusinessRules;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private AddressManager addressManager;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAdd() {

        CreateAddressRequest request = new CreateAddressRequest();
        Address address = new Address();
        CreatedAddressResponse response = new CreatedAddressResponse();

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forRequest().map(request, Address.class)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forResponse().map(address, CreatedAddressResponse.class)).thenReturn(response);
        when(addressBusinessRules.isCustomerExist(request)).thenReturn(address);

        CreatedAddressResponse result = addressManager.add(request);

        verify(addressBusinessRules).isCustomerExist(request);
        verify(addressRepository).save(address);

        verify(modelMapper).map(request,Address.class);

        verify(modelMapper).map(address,CreatedAddressResponse.class);
        assertEquals(response, result);
    }
    @Test
    void testAdd_whenCustomerDoesNotExist_thenThrowException() {

        CreateAddressRequest createAddressRequest = new CreateAddressRequest();

        doThrow(new BusinessException(AddressMessages.ADDRESS_DOES_NOT_EXIST)).when(addressBusinessRules).isCustomerExist(createAddressRequest);


        Exception exception = assertThrows(BusinessException.class, () -> {
            addressManager.add(createAddressRequest);
        });
        assertEquals(AddressMessages.ADDRESS_DOES_NOT_EXIST, exception.getMessage());
        verify(addressBusinessRules).isCustomerExist(createAddressRequest);
        verify(addressRepository, never()).save(any(Address.class));
    }

    @Test
    void testGetAll() {

        Address address = new Address();
        GetAllAddressResponse response = new GetAllAddressResponse();

        when(addressRepository.findByDeletedDateIsNull()).thenReturn(Collections.singletonList(address));
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forResponse().map(address, GetAllAddressResponse.class)).thenReturn(response);

        List<GetAllAddressResponse> result = addressManager.getAll();

        verify(addressRepository).findByDeletedDateIsNull();
        verify(modelMapperService.forResponse()).map(any(), eq(GetAllAddressResponse.class));

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
    }

    @Test
    void testGetById() {

        Address address = new Address();
        address.setId(1);
        GetByIdAddressResponse response = new GetByIdAddressResponse();

        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forResponse().map(address, GetByIdAddressResponse.class)).thenReturn(response);

        when(addressBusinessRules.isAddressAlreadyDeleted(address.getId())).thenReturn(address);

        GetByIdAddressResponse result = addressManager.getById(address.getId());


        verify(addressBusinessRules).isAddressAlreadyDeleted(address.getId());
        verify(modelMapperService.forResponse()).map(any(), eq(GetByIdAddressResponse.class));
        verify(addressRepository).findById(address.getId());

        assertEquals(response, result);
    }
    @Test
    void testGetById_whenAddressAlreadyDeleted_thenThrowException() {

        int id = 1;
        doThrow(new BusinessException(IndividualCustomerMessages.CUSTOMER_ALREADY_DELETED)).when(addressBusinessRules).isAddressAlreadyDeleted(id);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            addressManager.getById(id);
        });
        assertEquals(IndividualCustomerMessages.CUSTOMER_ALREADY_DELETED, exception.getMessage());
        verify(addressBusinessRules).isAddressAlreadyDeleted(id);
        verify(addressRepository, never()).findById(id);
    }

    @Test
    void testUpdate() {

        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setId(1);
        Address address = new Address();
        UpdatedAddressResponse response = new UpdatedAddressResponse();

        when(addressBusinessRules.isAddressAlreadyDeleted(request.getId())).thenReturn(address);
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forRequest().map(request, Address.class)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forResponse().map(address, UpdatedAddressResponse.class)).thenReturn(response);

        UpdatedAddressResponse result = addressManager.update(request);

        verify(addressBusinessRules).isAddressAlreadyDeleted(request.getId());
        verify(addressRepository).save(address);
        verify(modelMapperService.forResponse()).map(any(), eq(UpdatedAddressResponse.class));
        verify(modelMapperService.forRequest()).map(any(), eq(Address.class));

        assertEquals(response, result);
    }
    @Test
    void testUpdate_whenAddressAlreadyDeleted_thenThrowException() {

        UpdateAddressRequest updateAddressRequest = new UpdateAddressRequest();
        updateAddressRequest.setId(1);
        doThrow(new BusinessException(AddressMessages.ADDRESS_DOES_NOT_EXIST)).when(addressBusinessRules).isAddressAlreadyDeleted(updateAddressRequest.getId());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            addressManager.update(updateAddressRequest);
        });
        assertEquals(AddressMessages.ADDRESS_DOES_NOT_EXIST, exception.getMessage());
        verify(addressBusinessRules).isAddressAlreadyDeleted(updateAddressRequest.getId());
        verify(addressRepository, never()).save(any(Address.class));
    }
    @Test
    void testDelete() {

        int id = 1;
        Address address = new Address();

        when(addressBusinessRules.isAddressAlreadyDeleted(id)).thenReturn(address);

        addressManager.delete(id);

        verify(addressBusinessRules).isAddressAlreadyDeleted(id);
        verify(addressRepository).save(address);
    }

    @Test
    void testDelete_whenAddressAlreadyDeleted_thenThrowException(){

        int addressId=1;

        when(addressBusinessRules.isAddressAlreadyDeleted(addressId)).thenThrow(new BusinessException(AddressMessages.ADDRESS_ID_DOES_NOT_EXIST));

        assertThrows(BusinessException.class,()->{addressManager.delete(addressId);});

        verify(addressBusinessRules).isAddressAlreadyDeleted(addressId);
        verify(addressRepository, never()).save(any());
        verify(modelMapperService, never()).forRequest();
    }
}