package com.turkcell.turkcellcrm.customerService.business.concretes;

import com.turkcell.turkcellcrm.common.events.customer.CustomerCreatedEvent;
import com.turkcell.turkcellcrm.common.events.customer.CustomerUpdatedEvent;
import com.turkcell.turkcellcrm.customerService.business.abstracts.IndividualCustomerService;
import com.turkcell.turkcellcrm.customerService.business.dtos.request.customer.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.turkcellcrm.customerService.business.dtos.request.customer.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.customer.individualCustomer.CreatedIndividualCustomerResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.customer.individualCustomer.GetAllIndividualCustomerResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.customer.individualCustomer.GetByIdIndividualCustomerResponse;
import com.turkcell.turkcellcrm.customerService.business.dtos.response.customer.individualCustomer.UpdatedIndividualCustomerResponse;
import com.turkcell.turkcellcrm.customerService.business.rules.IndividualCustomerBusinessRules;
import com.turkcell.turkcellcrm.customerService.core.utilities.mapping.ModelMapperService;
import com.turkcell.turkcellcrm.customerService.dataAccess.IndividualCustomerRepository;
import com.turkcell.turkcellcrm.customerService.entity.Gender;
import com.turkcell.turkcellcrm.customerService.entity.IndividualCustomer;
import com.turkcell.turkcellcrm.customerService.kafka.producers.CustomerProducer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IndividualCustomerManager implements IndividualCustomerService {

    private IndividualCustomerRepository individualCustomerRepository;
    private ModelMapperService modelMapperService;
    private IndividualCustomerBusinessRules individualCustomerBusinessRules;
    private CustomerProducer customerProducer;

    @Override
    @Transactional
    public CreatedIndividualCustomerResponse add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {

        this.individualCustomerBusinessRules.nationalityNumberCanNotBeDuplicate(createIndividualCustomerRequest);

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().
                map(createIndividualCustomerRequest, IndividualCustomer.class);

        Gender gender = Gender.fromValue(createIndividualCustomerRequest.getGender());
        individualCustomer.setGender(gender);

        IndividualCustomer createdCustomer = this.individualCustomerRepository.save(individualCustomer);

        CustomerCreatedEvent customerCreatedEvent = this.modelMapperService.
                forRequest().map(createdCustomer,CustomerCreatedEvent.class);
        customerCreatedEvent.setCustomerId(createdCustomer.getId());

        this.customerProducer.sendCreatedMessage(customerCreatedEvent);

        return this.modelMapperService.forResponse().
                map(createdCustomer, CreatedIndividualCustomerResponse.class);
    }

    @Override
    public List<GetAllIndividualCustomerResponse> getAll() {

        List<IndividualCustomer> individualCustomers = this.individualCustomerRepository.findByDeletedDateIsNull();

        return  individualCustomers.stream().map(individualCustomer ->

                this.modelMapperService.forResponse().
                map(individualCustomer, GetAllIndividualCustomerResponse.class)).collect(Collectors.toList());
    }

    @Override
    public GetByIdIndividualCustomerResponse getById(int id) {

        this.individualCustomerBusinessRules.isCustomerAlreadyDeleted(id);
        this.individualCustomerBusinessRules.isCustomerIdExist(id);

        Optional<IndividualCustomer> individualCustomer =this.individualCustomerRepository.findById(id);

        return this.modelMapperService.forResponse().
                map(individualCustomer.get(), GetByIdIndividualCustomerResponse.class);
    }

    @Override
    @Transactional
    public UpdatedIndividualCustomerResponse update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {

        this.individualCustomerBusinessRules.isCustomerAlreadyDeleted(updateIndividualCustomerRequest.getId());
        this.individualCustomerBusinessRules.nationalityNumberCanNotBeDuplicate(updateIndividualCustomerRequest);

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().
                map(updateIndividualCustomerRequest,IndividualCustomer.class);

        CustomerUpdatedEvent customerUpdatedEvent = this.modelMapperService.forResponse().
                map(individualCustomer, CustomerUpdatedEvent.class);
        this.customerProducer.sendUpdatedMessage(customerUpdatedEvent);

        return this.modelMapperService.forResponse().
                map(this.individualCustomerRepository.save(individualCustomer), UpdatedIndividualCustomerResponse.class);
    }

    @Override
    @Transactional
    public void delete(int id) {

        IndividualCustomer individualCustomer =this.individualCustomerBusinessRules.isCustomerAlreadyDeleted(id);
        individualCustomer.setDeletedDate(LocalDateTime.now());

        this.customerProducer.sendDeletedMessage(id);

        this.individualCustomerRepository.save(individualCustomer);
    }

     @Override
     public int getAddresIdByCustomerId(int customerId){

        IndividualCustomer individualCustomer = this.individualCustomerBusinessRules.isCustomerIdExist(customerId);

        return individualCustomer.getId();
     }
}
