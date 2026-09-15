package com.hybridpos.report_service.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hybridpos.report_service.entity.SaleReport;
import com.hybridpos.report_service.entity.SaleReportItem;
import com.hybridpos.report_service.repository.SaleReportItemRepository;
import com.hybridpos.report_service.repository.SaleReportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleEventConsumer {
    private final SaleReportRepository saleReportRepository;
    private final SaleReportItemRepository saleReportItemRepository;

    @Transactional
    @KafkaListener(topics = "sale-created", groupId = "report-service-group")
    public void consume(SaleCreatedEvent event) {
        if (saleReportRepository.existsBySaleId(event.getSaleId())) {
            System.out.println(
                    "Sale event zaten işlendi: " + event.getSaleId());
            return;
        }
        SaleReport report = new SaleReport();

        report.setSaleId(event.getSaleId());
        report.setCashRegisterId(event.getCashRegisterId());
        report.setTotalAmount(event.getTotalAmount());
        report.setCashPaid(event.getCashPaid());
        report.setCardPaid(event.getCardPaid());
        report.setPaymentType(event.getPaymentType());
        report.setCreatedAt(event.getCreatedAt());

        saleReportRepository.save(report);

        for (SaleCreatedItemEvent eventItem : event.getItems()) {

            SaleReportItem item = new SaleReportItem();

            item.setSaleId(event.getSaleId());
            item.setProductId(eventItem.getProductId());
            item.setBarcode(eventItem.getBarcode());
            item.setProductName(eventItem.getProductName());
            item.setPriceAtSale(eventItem.getPriceAtSale());
            item.setQuantity(eventItem.getQuantity());
            item.setCreatedAt(event.getCreatedAt());

            saleReportItemRepository.save(item);
        }

        System.out.println("SaleReport ve SaleReportItem veritabanına kaydedildi!");
    }
}