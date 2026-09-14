package com.hybridpos.report_service.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SaleEventConsumer {

    @KafkaListener(
            topics = "sale-created",
            groupId = "report-service-group"
    )
    public void consume(SaleCreatedEvent event) {

        System.out.println("====================================");
        System.out.println("REPORT SERVICE - SaleCreatedEvent alındı!");
        System.out.println("Sale ID: " + event.getSaleId());
        System.out.println("Cash Register ID: " + event.getCashRegisterId());
        System.out.println("Total Amount: " + event.getTotalAmount());
        System.out.println("Cash Paid: " + event.getCashPaid());
        System.out.println("Card Paid: " + event.getCardPaid());
        System.out.println("Payment Type: " + event.getPaymentType());
        System.out.println("Created At: " + event.getCreatedAt());
        System.out.println("====================================");
    }
}