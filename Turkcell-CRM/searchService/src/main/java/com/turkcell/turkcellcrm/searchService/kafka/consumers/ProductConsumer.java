package com.turkcell.turkcellcrm.searchService.kafka.consumers;

import com.turkcell.turkcellcrm.common.events.product.ProductCreatedEvent;
import com.turkcell.turkcellcrm.common.events.product.ProductUpdatedEvent;
import com.turkcell.turkcellcrm.searchService.business.abstracts.SearchProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ProductConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductConsumer.class);
    private SearchProductService searchProductService;


    @KafkaListener(topics = "product-created", groupId = "product_group-1")
    public void listenProductCreated(ProductCreatedEvent productCreatedEvent) {

        this.searchProductService.add(productCreatedEvent);
    }
    @KafkaListener(topics = "product-updated", groupId = "product_group-2")
    public void listenProductUpdated(ProductUpdatedEvent productUpdatedEvent) {

        this.searchProductService.update(productUpdatedEvent);
    }

    @KafkaListener(topics = "product-deleted", groupId = "product_group-3")
    public void listenProductDeleted(ProductUpdatedEvent productUpdatedEvent) {

        this.searchProductService.delete(productUpdatedEvent.getProductId());

    }
}
