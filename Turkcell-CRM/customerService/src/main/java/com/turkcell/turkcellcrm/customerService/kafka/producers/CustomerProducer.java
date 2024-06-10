package com.turkcell.turkcellcrm.customerService.kafka.producers;


import com.turkcell.turkcellcrm.common.events.customer.CustomerCreatedEvent;
import com.turkcell.turkcellcrm.common.events.customer.CustomerDeletedEvent;
import com.turkcell.turkcellcrm.common.events.customer.CustomerUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class CustomerProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CustomerProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCreatedMessage(CustomerCreatedEvent customerCreatedEvent) {

        LOGGER.info(String.format("Customer added =>%s", customerCreatedEvent.toString()));

        Message<CustomerCreatedEvent> message = MessageBuilder.withPayload(customerCreatedEvent)
                .setHeader(KafkaHeaders.TOPIC, "customer-created")
                .build();

        kafkaTemplate.send(message);
    }

    public void sendUpdatedMessage(CustomerUpdatedEvent customerUpdatedEvent) {

        LOGGER.info(String.format("Customer updated =>%s", customerUpdatedEvent.toString()));

        Message<CustomerUpdatedEvent> message = MessageBuilder.withPayload(customerUpdatedEvent)
                .setHeader(KafkaHeaders.TOPIC, "customer-updated")
                .build();

        kafkaTemplate.send(message);
    }

    public void sendDeletedMessage(int id) {

        CustomerDeletedEvent customerDeletedEvent = new CustomerDeletedEvent(id);
        LOGGER.info(String.format("Customer deleted =>%s", customerDeletedEvent.toString()));

        Message<CustomerDeletedEvent> message = MessageBuilder.withPayload(customerDeletedEvent)
                .setHeader(KafkaHeaders.TOPIC, "customer-deleted")
                .build();

        kafkaTemplate.send(message);
    }
}