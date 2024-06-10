package com.turkcell.turkcellcrm.searchService.kafka.consumers;


import com.turkcell.turkcellcrm.common.events.customer.CustomerCreatedEvent;
import com.turkcell.turkcellcrm.common.events.customer.CustomerDeletedEvent;
import com.turkcell.turkcellcrm.common.events.customer.CustomerUpdatedEvent;
import com.turkcell.turkcellcrm.searchService.business.abstracts.SearchCustomerService;
import com.turkcell.turkcellcrm.searchService.core.utilities.mapping.ModelMapperService;
import com.turkcell.turkcellcrm.searchService.entities.Customer;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomerConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerConsumer.class);
    private SearchCustomerService searchCustomerService;
    private ModelMapperService modelMapperService;

    @KafkaListener(topics = "customer-created", groupId = "customer_group-1")
    public void listenCustomerCreated(CustomerCreatedEvent customerCreatedEvent) {

        this.searchCustomerService.add(customerCreatedEvent);

    }

    @KafkaListener(topics = "customer-updated", groupId = "customer_group-2")
    public void listenCustomerUpdated(CustomerUpdatedEvent customerUpdatedEvent) {

        this.searchCustomerService.update(customerUpdatedEvent);

   }

   @KafkaListener(topics = "customer-deleted", groupId = "customer_group-3")
    public void listenCustomerDeleted(CustomerDeletedEvent customerDeletedEvent) {

      this.searchCustomerService.deleteCustomer(customerDeletedEvent.getCustomerId());

    }
}
