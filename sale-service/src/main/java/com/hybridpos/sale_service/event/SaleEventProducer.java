package com.hybridpos.sale_service.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleEventProducer {

    private static final String TOPIC = "sale-created";

    private final KafkaTemplate<String, SaleCreatedEvent> kafkaTemplate;

    public void sendSaleCreatedEvent(SaleCreatedEvent event) {
        kafkaTemplate.send(TOPIC, event.getSaleId().toString(), event);
    }
}