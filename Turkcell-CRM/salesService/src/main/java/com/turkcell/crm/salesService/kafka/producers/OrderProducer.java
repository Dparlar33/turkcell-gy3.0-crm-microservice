package com.turkcell.crm.salesService.kafka.producers;

import com.turkcell.turkcellcrm.common.events.order.OrderCreatedEvent;
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
public class OrderProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendCreatedMessage(OrderCreatedEvent orderCreatedEvent) {
        LOGGER.info(String.format("Order added =>%s", orderCreatedEvent.toString()));

        Message<OrderCreatedEvent> message = MessageBuilder.withPayload(orderCreatedEvent)
                .setHeader(KafkaHeaders.TOPIC, "order-created")
                .build();

        kafkaTemplate.send(message);
    }
}