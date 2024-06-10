package com.turkcell.crm.catalogService.kafka.producers;

import com.turkcell.turkcellcrm.common.events.catalog.CatalogCreatedEvent;
import com.turkcell.turkcellcrm.common.events.catalog.CatalogDeletedEvent;
import com.turkcell.turkcellcrm.common.events.catalog.CatalogUpdatedEvent;
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
public class CatalogProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendCreatedMessage(CatalogCreatedEvent catalogCreatedEvent) {

        LOGGER.info(String.format("Catalog added =>%s", catalogCreatedEvent.toString()));

        Message<CatalogCreatedEvent> message = MessageBuilder.withPayload(catalogCreatedEvent)
                .setHeader(KafkaHeaders.TOPIC, "catalog-created")
                .build();

        kafkaTemplate.send(message);
    }

    public void sendUpdatedMessage(CatalogUpdatedEvent catalogUpdatedEvent) {

        LOGGER.info(String.format("Catalog updated =>%s",catalogUpdatedEvent.toString()));

        Message<CatalogUpdatedEvent> message = MessageBuilder.withPayload(catalogUpdatedEvent)
                .setHeader(KafkaHeaders.TOPIC, "catalog-updated")
                .build();

        kafkaTemplate.send(message);
    }

    public void sendDeletedMessage(CatalogDeletedEvent catalogDeletedEvent) {

        LOGGER.info(String.format("Catalog deleted =>%s", catalogDeletedEvent.toString()));

        Message<CatalogDeletedEvent> message = MessageBuilder.withPayload(catalogDeletedEvent)
                .setHeader(KafkaHeaders.TOPIC, "catalog-deleted")
                .build();

        kafkaTemplate.send(message);
    }

}