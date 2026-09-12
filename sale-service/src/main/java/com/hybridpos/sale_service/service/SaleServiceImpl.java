package com.hybridpos.sale_service.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.hybridpos.sale_service.client.CashClient;
import com.hybridpos.sale_service.client.ProductClient;
import com.hybridpos.sale_service.client.ProductClient.ProductResponse;
import com.hybridpos.sale_service.dto.SaleCreateDTO;
import com.hybridpos.sale_service.dto.SaleItemDTO;
import com.hybridpos.sale_service.dto.SaleReportDTO;
import com.hybridpos.sale_service.dto.SaleResponseDTO;
import com.hybridpos.sale_service.entity.Sale;
import com.hybridpos.sale_service.entity.SaleItem;
import com.hybridpos.sale_service.repository.SaleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

        private final SaleRepository saleRepository;
        private final ProductClient productClient;
        private final CashClient cashClient;

        @Override
        @Transactional
        public SaleResponseDTO makeSale(
                        Long cashId,
                        SaleCreateDTO dto,
                        String token) {
                cashClient.validateSale(cashId, token);

                Sale sale = new Sale();

                sale.setCashId(cashId);
                sale.setSaleDate(LocalDateTime.now());
                sale.setPaymentType(dto.getPaymentType());
                sale.setCashPaid(dto.getCashPaid());
                sale.setCardPaid(dto.getCardPaid());

                List<SaleItem> items = new ArrayList<>();

                BigDecimal total = BigDecimal.ZERO;

                for (SaleItemDTO itemDTO : dto.getItems()) {

                        // Product Service'ten ürünü al
                        ProductResponse product = productClient.getProductByBarcode(
                                        itemDTO.getBarcode(),
                                        token);

                        // Stok kontrolü
                        if (product.getStockQuantity() < itemDTO.getQuantity()) {
                                throw new RuntimeException(
                                                "Insufficient stock for product: "
                                                                + product.getName());
                        }

                        productClient.updateStock(
                                        product.getId(),
                                        -itemDTO.getQuantity(),
                                        token);

                        SaleItem item = new SaleItem();

                        item.setProductId(product.getId());
                        item.setBarcode(product.getBarcode());
                        item.setProductName(product.getName());
                        item.setQuantity(itemDTO.getQuantity());

                        // Satış anındaki gerçek fiyatı Product Service'ten alıyoruz.
                        item.setPriceAtSale(product.getSalePrice());

                        item.setSale(sale);

                        BigDecimal itemTotal = product.getSalePrice()
                                        .multiply(
                                                        BigDecimal.valueOf(
                                                                        itemDTO.getQuantity()));

                        total = total.add(itemTotal);

                        items.add(item);
                }

                sale.setTotalPrice(total);
                sale.setItems(items);

                Sale savedSale = saleRepository.save(sale);

                cashClient.processSale(
                                cashId,
                                dto.getCashPaid(),
                                dto.getCardPaid(),
                                token);

                return mapToResponse(savedSale);
        }

        @Override
        public SaleReportDTO getReport(
                        LocalDateTime start,
                        LocalDateTime end) {

                List<Sale> sales = saleRepository.findBySaleDateBetween(start, end);

                BigDecimal totalRevenue = sales.stream()
                                .map(Sale::getTotalPrice)
                                .reduce(
                                                BigDecimal.ZERO,
                                                BigDecimal::add);

                BigDecimal totalCash = sales.stream()
                                .map(Sale::getCashPaid)
                                .filter(Objects::nonNull)
                                .reduce(
                                                BigDecimal.ZERO,
                                                BigDecimal::add);

                BigDecimal totalCard = sales.stream()
                                .map(Sale::getCardPaid)
                                .filter(Objects::nonNull)
                                .reduce(
                                                BigDecimal.ZERO,
                                                BigDecimal::add);

                SaleReportDTO report = new SaleReportDTO();

                report.setTotalSales(sales.size());
                report.setTotalRevenue(totalRevenue);
                report.setTotalCash(totalCash);
                report.setTotalCard(totalCard);

                return report;
        }

        private SaleResponseDTO mapToResponse(Sale sale) {

                SaleResponseDTO dto = new SaleResponseDTO();

                dto.setSaleId(sale.getId());
                dto.setTotalAmount(sale.getTotalPrice());
                dto.setDate(sale.getSaleDate());

                return dto;
        }
}