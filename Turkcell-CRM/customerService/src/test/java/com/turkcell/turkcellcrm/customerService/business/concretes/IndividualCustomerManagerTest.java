package com.turkcell.turkcellcrm.customerService.business.concretes;

import com.turkcell.turkcellcrm.common.events.customer.CustomerCreatedEvent;
import com.turkcell.turkcellcrm.common.events.customer.CustomerUpdatedEvent;
import com.turkcell.turkcellcrm.customerService.business.dtos.request.customer.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.turkcellcrm.customerService.business.dtos.request.customer.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.customer.individualCustomer.CreatedIndividualCustomerResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.customer.individualCustomer.GetAllIndividualCustomerResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.customer.individualCustomer.GetByIdIndividualCustomerResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.customer.individualCustomer.UpdatedIndividualCustomerResponse;
import com.turkcell.turkcellcrm.customerService.business.messages.IndividualCustomerMessages;
import com.turkcell.turkcellcrm.customerService.business.rules.IndividualCustomerBusinessRules;
import com.turkcell.turkcellcrm.customerService.core.utilities.exceptions.types.BusinessException;
import com.turkcell.turkcellcrm.customerService.core.utilities.mapping.ModelMapperService;
import com.turkcell.turkcellcrm.customerService.dataAccess.IndividualCustomerRepository;
import com.turkcell.turkcellcrm.customerService.entity.IndividualCustomer;
import com.turkcell.turkcellcrm.customerService.kafka.producers.CustomerProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IndividualCustomerManagerTest {
    @Mock
    private IndividualCustomerRepository individualCustomerRepository;
    @Mock
    private ModelMapperService modelMapperService;
    @Mock
    private IndividualCustomerBusinessRules individualCustomerBusinessRules;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private CustomerProducer customerProducer;
    @InjectMocks
    private IndividualCustomerManager individualCustomerManager;
    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAdd() {

        CreateIndividualCustomerRequest request = new CreateIndividualCustomerRequest();
        IndividualCustomer individualCustomer = new IndividualCustomer();
        CreatedIndividualCustomerResponse response = new CreatedIndividualCustomerResponse();
        CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent();

        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forRequest().map(request, IndividualCustomer.class)).thenReturn(individualCustomer);
        when(individualCustomerRepository.save(individualCustomer)).thenReturn(individualCustomer);
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forResponse().map(individualCustomer, CreatedIndividualCustomerResponse.class)).thenReturn(response);
        when(modelMapperService.forRequest().map(individualCustomer, CustomerCreatedEvent.class)).thenReturn(customerCreatedEvent);
        doNothing().when(individualCustomerBusinessRules).nationalityNumberCanNotBeDuplicate(request);

        CreatedIndividualCustomerResponse result = individualCustomerManager.add(request);

        verify(individualCustomerBusinessRules).nationalityNumberCanNotBeDuplicate(request);
        verify(individualCustomerRepository).save(individualCustomer);
        verify(modelMapperService.forRequest()).map(request, IndividualCustomer.class);
        verify(modelMapperService.forResponse()).map(individualCustomer, CreatedIndividualCustomerResponse.class);
        verify(customerProducer).sendCreatedMessage(customerCreatedEvent);
        assertEquals(response, result);
    }

    @Test
    void testAdd_whenNationalityNumberDuplicate_thenThrowException() {

        CreateIndividualCustomerRequest request = new CreateIndividualCustomerRequest();
        doThrow(new BusinessException(IndividualCustomerMessages.NATIONALITY_ID_CAN_NOT_DUPLICATE)).when(individualCustomerBusinessRules).nationalityNumberCanNotBeDuplicate(request);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            individualCustomerManager.add(request);
        });
        assertEquals(IndividualCustomerMessages.NATIONALITY_ID_CAN_NOT_DUPLICATE, exception.getMessage());
        verify(individualCustomerBusinessRules).nationalityNumberCanNotBeDuplicate(request);
        verify(individualCustomerRepository, never()).save(any(IndividualCustomer.class));
    }

    @Test
    public void testGetAll() {

        IndividualCustomer customer1 = new IndividualCustomer();
        customer1.setId(1);
        customer1.setFirstName("john");

        IndividualCustomer customer2 = new IndividualCustomer();
        customer2.setId(2);
        customer2.setFirstName("Jane Doe");

        List<IndividualCustomer> customers = Arrays.asList(customer1, customer2);
        when(individualCustomerRepository.findByDeletedDateIsNull()).thenReturn(customers);

        GetAllIndividualCustomerResponse response1 = new GetAllIndividualCustomerResponse();
        response1.setId(1);
        response1.setFirstName("John Doe");

        GetAllIndividualCustomerResponse response2 = new GetAllIndividualCustomerResponse();
        response2.setId(2);
        response2.setFirstName("Jane Doe");

        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forResponse().map(customer1, GetAllIndividualCustomerResponse.class)).thenReturn(response1);
        when(modelMapperService.forResponse().map(customer2, GetAllIndividualCustomerResponse.class)).thenReturn(response2);

        List<GetAllIndividualCustomerResponse> result = individualCustomerManager.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getFirstName()).isEqualTo("John Doe");
        assertThat(result.get(1).getFirstName()).isEqualTo("Jane Doe");

        verify(individualCustomerRepository).findByDeletedDateIsNull();
        verify(modelMapperService.forResponse(), times(2)).map(any(), eq(GetAllIndividualCustomerResponse.class));
    }

    @Test
    void testGetById() {

        int id = 1;
        IndividualCustomer individualCustomer = new IndividualCustomer();
        GetByIdIndividualCustomerResponse response = new GetByIdIndividualCustomerResponse();

        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(individualCustomerRepository.findById(id)).thenReturn(Optional.of(individualCustomer));
        when(modelMapperService.forResponse().map(individualCustomer, GetByIdIndividualCustomerResponse.class)).thenReturn(response);

        GetByIdIndividualCustomerResponse result = individualCustomerManager.getById(id);

        verify(individualCustomerBusinessRules).isCustomerAlreadyDeleted(id);
        verify(individualCustomerBusinessRules).isCustomerIdExist(id);
        verify(individualCustomerRepository).findById(id);
        verify(modelMapperService.forResponse()).map(individualCustomer, GetByIdIndividualCustomerResponse.class);
        assertEquals(response, result);
    }

    @Test
    void testGetById_whenCustomerAlreadyDeleted_thenThrowException() {

        int id = 1;
        doThrow(new BusinessException(IndividualCustomerMessages.CUSTOMER_ALREADY_DELETED)).when(individualCustomerBusinessRules).isCustomerAlreadyDeleted(id);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            individualCustomerManager.getById(id);
        });
        assertEquals(IndividualCustomerMessages.CUSTOMER_ALREADY_DELETED, exception.getMessage());
        verify(individualCustomerBusinessRules).isCustomerAlreadyDeleted(id);
        verify(individualCustomerRepository, never()).findById(id);
    }

    @Test
    void testGetById_whenCustomerIdNotExist_thenThrowException() {

        int id = 1;
        doThrow(new BusinessException(IndividualCustomerMessages.CUSTOMER_DOES_NOT_EXIST)).when(individualCustomerBusinessRules).isCustomerIdExist(id);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            individualCustomerManager.getById(id);
        });
        assertEquals(IndividualCustomerMessages.CUSTOMER_DOES_NOT_EXIST, exception.getMessage());
        verify(individualCustomerBusinessRules).isCustomerIdExist(id);
        verify(individualCustomerRepository, never()).findById(id);
    }

    @Test
    void testUpdate() {

        UpdateIndividualCustomerRequest updateRequest = new UpdateIndividualCustomerRequest();
        updateRequest.setId(1);
        IndividualCustomer individualCustomer = new IndividualCustomer();
        UpdatedIndividualCustomerResponse response = new UpdatedIndividualCustomerResponse();
        CustomerUpdatedEvent customerUpdatedEvent = new CustomerUpdatedEvent();

        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapperService.forRequest().map(updateRequest, IndividualCustomer.class)).thenReturn(individualCustomer);
        when(modelMapperService.forResponse().map(individualCustomer, UpdatedIndividualCustomerResponse.class)).thenReturn(response);
        when(individualCustomerRepository.save(individualCustomer)).thenReturn(individualCustomer);
        when(modelMapperService.forResponse().map(individualCustomer, CustomerUpdatedEvent.class)).thenReturn(customerUpdatedEvent);

        UpdatedIndividualCustomerResponse result = individualCustomerManager.update(updateRequest);

        verify(individualCustomerBusinessRules).isCustomerAlreadyDeleted(updateRequest.getId());
        verify(individualCustomerBusinessRules).nationalityNumberCanNotBeDuplicate(updateRequest);
        verify(modelMapperService.forRequest()).map(updateRequest, IndividualCustomer.class);
        verify(modelMapperService.forResponse()).map(individualCustomer, UpdatedIndividualCustomerResponse.class);
        verify(customerProducer).sendUpdatedMessage(customerUpdatedEvent);
        assertEquals(response, result);
    }

    @Test
    void testUpdate_whenCustomerAlreadyDeleted_thenThrowException() {

        UpdateIndividualCustomerRequest updateRequest = new UpdateIndividualCustomerRequest();
        updateRequest.setId(1);
        doThrow(new BusinessException(IndividualCustomerMessages.CUSTOMER_ALREADY_DELETED)).when(individualCustomerBusinessRules).isCustomerAlreadyDeleted(updateRequest.getId());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            individualCustomerManager.update(updateRequest);
        });
        assertEquals(IndividualCustomerMessages.CUSTOMER_ALREADY_DELETED, exception.getMessage());
        verify(individualCustomerBusinessRules).isCustomerAlreadyDeleted(updateRequest.getId());
        verify(individualCustomerRepository, never()).save(any(IndividualCustomer.class));
    }

    @Test
    void testUpdate_whenNationalityNumberDuplicate_thenThrowException() {

        UpdateIndividualCustomerRequest updateRequest = new UpdateIndividualCustomerRequest();
        doThrow(new BusinessException(IndividualCustomerMessages.NATIONALITY_ID_CAN_NOT_DUPLICATE)).when(individualCustomerBusinessRules).nationalityNumberCanNotBeDuplicate(updateRequest);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            individualCustomerManager.update(updateRequest);
        });
        assertEquals(IndividualCustomerMessages.NATIONALITY_ID_CAN_NOT_DUPLICATE, exception.getMessage());
        verify(individualCustomerBusinessRules).nationalityNumberCanNotBeDuplicate(updateRequest);
        verify(individualCustomerRepository, never()).save(any(IndividualCustomer.class));
    }

    @Test
    void testDelete() {

        int id = 1;
        IndividualCustomer individualCustomer = new IndividualCustomer();
        when(individualCustomerBusinessRules.isCustomerAlreadyDeleted(id)).thenReturn(individualCustomer);

        individualCustomerManager.delete(id);

        verify(individualCustomerBusinessRules).isCustomerAlreadyDeleted(id);
        verify(customerProducer).sendDeletedMessage(id);
        verify(individualCustomerRepository).save(individualCustomer);
        assertNotNull(individualCustomer.getDeletedDate());
    }

    @Test
    void testDelete_whenCustomerAlreadyDeleted_thenThrowException() {

        int id = 1;
        doThrow(new BusinessException(IndividualCustomerMessages.CUSTOMER_ALREADY_DELETED)).when(individualCustomerBusinessRules).isCustomerAlreadyDeleted(id);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            individualCustomerManager.delete(id);
        });
        assertEquals(IndividualCustomerMessages.CUSTOMER_ALREADY_DELETED, exception.getMessage());
        verify(individualCustomerBusinessRules).isCustomerAlreadyDeleted(id);
        verify(individualCustomerRepository, never()).save(any(IndividualCustomer.class));
    }

    @Test
    void testGetAddresIdByCustomerId() {

        IndividualCustomer individualCustomer = new IndividualCustomer();
        individualCustomer.setId(1);
        when(individualCustomerBusinessRules.isCustomerIdExist(individualCustomer.getId())).thenReturn(individualCustomer);

        int result = individualCustomerManager.getAddresIdByCustomerId(individualCustomer.getId());

        verify(individualCustomerBusinessRules).isCustomerIdExist(individualCustomer.getId());
        assertEquals(individualCustomer.getId(), result);
    }

    @Test
    void testGetAddresIdByCustomerId_whenCustomerIdNotExist_thenThrowException() {
        int customerId = 1;
        doThrow(new BusinessException(IndividualCustomerMessages.CUSTOMER_DOES_NOT_EXIST)).when(individualCustomerBusinessRules).isCustomerIdExist(customerId);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            individualCustomerManager.getAddresIdByCustomerId(customerId);
        });
        assertEquals(IndividualCustomerMessages.CUSTOMER_DOES_NOT_EXIST, exception.getMessage());
        verify(individualCustomerBusinessRules).isCustomerIdExist(customerId);
    }
}