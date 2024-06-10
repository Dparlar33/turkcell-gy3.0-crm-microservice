package com.turkcell.crm.catalogService.kafka.producers;

import com.turkcell.turkcellcrm.common.events.product.ProductCreatedEvent;
import com.turkcell.turkcellcrm.common.events.product.ProductDeletedEvent;
import com.turkcell.turkcellcrm.common.events.product.ProductUpdatedEvent;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ProductProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendCreatedMessage(ProductCreatedEvent productCreatedEvent) {

        LOGGER.info(String.format("Product added =>%s", productCreatedEvent.toString()));

        Message<ProductCreatedEvent> message = MessageBuilder.withPayload(productCreatedEvent)
                .setHeader(KafkaHeaders.TOPIC, "product-created")
                .build();

        kafkaTemplate.send(message);
    }

    public void sendUpdatedMessage(ProductUpdatedEvent productUpdatedEvent) {

        LOGGER.info(String.format("Product updated =>%s",productUpdatedEvent.toString()));

        Message<ProductUpdatedEvent> message = MessageBuilder.withPayload(productUpdatedEvent)
                .setHeader(KafkaHeaders.TOPIC, "product-updated")
                .build();

        kafkaTemplate.send(message);
    }

    public void sendDeletedMessage(ProductDeletedEvent productDeletedEvent) {

        LOGGER.info(String.format("Product deleted =>%s", productDeletedEvent.toString()));

        Message<ProductDeletedEvent> message = MessageBuilder.withPayload(productDeletedEvent)
                .setHeader(KafkaHeaders.TOPIC, "product-deleted")
                .build();

        kafkaTemplate.send(message);
    }

}
