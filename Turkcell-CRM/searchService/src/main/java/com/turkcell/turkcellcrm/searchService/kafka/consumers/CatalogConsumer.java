package com.turkcell.turkcellcrm.searchService.kafka.consumers;

import com.turkcell.turkcellcrm.common.events.catalog.CatalogCreatedEvent;
import com.turkcell.turkcellcrm.common.events.catalog.CatalogDeletedEvent;
import com.turkcell.turkcellcrm.common.events.catalog.CatalogUpdatedEvent;
import com.turkcell.turkcellcrm.searchService.business.abstracts.SearchCatalogService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CatalogConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductConsumer.class);
    private SearchCatalogService searchCatalogService;


    @KafkaListener(topics = "catalog-created", groupId = "catalog_group-1")
    public void listenCatalogCreated(CatalogCreatedEvent catalogCreatedEvent) {

        this.searchCatalogService.add(catalogCreatedEvent);
    }

    @KafkaListener(topics = "catalog-updated", groupId = "catalog_group-2")
    public void listenCatalogUpdated(CatalogUpdatedEvent catalogUpdatedEvent) {

        this.searchCatalogService.update(catalogUpdatedEvent);
    }

    @KafkaListener(topics = "catalog-deleted", groupId = "catalog_group-3")
    public void listenCatalogDeleted(CatalogDeletedEvent catalogDeletedEvent) {

        this.searchCatalogService.delete(catalogDeletedEvent.getId());

    }
}
