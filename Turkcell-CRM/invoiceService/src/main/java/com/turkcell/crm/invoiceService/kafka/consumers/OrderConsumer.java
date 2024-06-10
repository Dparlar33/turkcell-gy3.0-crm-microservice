package com.turkcell.crm.invoiceService.kafka.consumers;

import com.turkcell.crm.invoiceService.business.abstracts.InvoiceService;
import com.turkcell.turkcellcrm.common.events.order.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);
    private InvoiceService invoiceService;

    @KafkaListener(topics = "order-created", groupId = "order_group-1")
    public void listenOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        this.invoiceService.add(orderCreatedEvent);
    }
}
