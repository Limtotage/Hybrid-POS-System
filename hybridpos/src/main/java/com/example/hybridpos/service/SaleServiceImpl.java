package com.example.hybridpos.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hybridpos.dto.SaleCreateDTO;
import com.example.hybridpos.dto.SaleItemDTO;
import com.example.hybridpos.dto.SaleResponseDTO;
import com.example.hybridpos.entity.CashRegister;
import com.example.hybridpos.entity.Product;
import com.example.hybridpos.entity.Sale;
import com.example.hybridpos.entity.SaleItem;
import com.example.hybridpos.repository.CashRegisterRepository;
import com.example.hybridpos.repository.ProductRepository;
import com.example.hybridpos.repository.SaleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final CashRegisterRepository cashRegisterRepository;

    @Override
    @Transactional
    public SaleResponseDTO makeSale(long cashId, SaleCreateDTO dto) {

        CashRegister cash = cashRegisterRepository.findById(cashId)
                .orElseThrow();

        Sale sale = new Sale();
        sale.setCashRegister(cash);
        sale.setSaleDate(LocalDateTime.now());
        sale.setPaymentType(dto.getPaymentType());
        sale.setCashPaid(dto.getCashPaid());
        sale.setCardPaid(dto.getCardPaid());

        List<SaleItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (SaleItemDTO itemDTO : dto.getItems()) {

            Product product = productRepository
                    .findByBarcode(itemDTO.getBarcode())
                    .orElseThrow();

            if (product.getStockQuantity() < itemDTO.getQuantity()) {
                throw new RuntimeException("Insufficient stock");
            }

            product.setStockQuantity(
                    product.getStockQuantity() - itemDTO.getQuantity());

            SaleItem item = new SaleItem();
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setPriceAtSale(product.getSalePrice());
            item.setSale(sale);

            total = total.add(BigDecimal.valueOf(item.getQuantity()).multiply(item.getPriceAtSale()));
            items.add(item);
        }

        sale.setTotalPrice(total);
        sale.setItems(items);

        saleRepository.save(sale);

        cash.setTotalCashSales(cash.getTotalCashSales().add(dto.getCashPaid()));
        cash.setTotalCardSales(cash.getTotalCardSales().add(dto.getCardPaid()));
        cashRegisterRepository.save(cash);
        return mapToResponse(sale);
    }

    @Override
    public SaleResponseDTO getReport(LocalDateTime startDate, LocalDateTime endDate) {
        List<Sale> sales = saleRepository.findBySaleDateBetween(startDate, endDate);
        BigDecimal totalAmount = sales.stream().map(Sale::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        SaleResponseDTO report = new SaleResponseDTO();
        report.setTotalAmount(totalAmount);
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
