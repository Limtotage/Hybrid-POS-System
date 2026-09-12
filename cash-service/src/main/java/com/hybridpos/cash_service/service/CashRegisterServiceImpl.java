package com.hybridpos.cash_service.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hybridpos.cash_service.dto.CashRegisterReportDTO;
import com.hybridpos.cash_service.dto.CashSaleDTO;
import com.hybridpos.cash_service.entity.CashRegister;
import com.hybridpos.cash_service.repository.CashRegisterRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashRegisterServiceImpl
                implements CashRegisterService {

        private final CashRegisterRepository cashRegisterRepository;

        @Override
        public CashRegister createCashRegister(String name) {

                CashRegister cashRegister = new CashRegister();

                cashRegister.setName(name);

                cashRegister.setTotalCashSales(BigDecimal.ZERO);
                cashRegister.setTotalCardSales(BigDecimal.ZERO);
                cashRegister.setTotalSales(BigDecimal.ZERO);

                cashRegister.setCreatedAt(LocalDateTime.now());
                cashRegister.setOpen(true);

                return cashRegisterRepository.save(cashRegister);
        }

        @Override
        public List<CashRegister> getAllCashRegisters() {

                return cashRegisterRepository.findAll();
        }

        @Override
        public CashRegister getCashRegister(Long id) {

                return cashRegisterRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Cash register not found"));
        }

        @Override
        public void deleteCashRegister(Long id) {

                CashRegister cashRegister = cashRegisterRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "Cash register not found"));

                cashRegisterRepository.delete(cashRegister);
        }

        @Override
        public void processSale(Long cashRegisterId, CashSaleDTO dto) {

                CashRegister cashRegister = cashRegisterRepository.findById(cashRegisterId)
                                .orElseThrow(() -> new RuntimeException("Cash register not found"));

                if (!cashRegister.isOpen()) {
                        throw new RuntimeException("Cash register is closed");
                }

                BigDecimal cashPaid = dto.getCashPaid() != null
                                ? dto.getCashPaid()
                                : BigDecimal.ZERO;

                BigDecimal cardPaid = dto.getCardPaid() != null
                                ? dto.getCardPaid()
                                : BigDecimal.ZERO;

                BigDecimal totalSale = cashPaid.add(cardPaid);

                cashRegister.setTotalCashSales(
                                cashRegister.getTotalCashSales().add(cashPaid));

                cashRegister.setTotalCardSales(
                                cashRegister.getTotalCardSales().add(cardPaid));

                cashRegister.setTotalSales(
                                cashRegister.getTotalSales().add(totalSale));

                cashRegisterRepository.save(cashRegister);
        }

        @Override
        public void closeCashRegister(Long id) {

                CashRegister cashRegister = cashRegisterRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Cash register not found"));

                if (!cashRegister.isOpen()) {
                        throw new RuntimeException("Cash register is already closed");
                }

                cashRegister.setOpen(false);

                cashRegisterRepository.save(cashRegister);
        }

        @Override
        public void openCashRegister(Long id) {

                CashRegister cashRegister = cashRegisterRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Cash register not found"));

                if (cashRegister.isOpen()) {
                        throw new RuntimeException("Cash register is already open");
                }

                cashRegister.setOpen(true);

                cashRegisterRepository.save(cashRegister);
        }

        @Override
        public boolean validateSale(Long cashRegisterId) {

                CashRegister cashRegister = cashRegisterRepository.findById(cashRegisterId)
                                .orElseThrow(() -> new RuntimeException("Cash register not found"));

                if (!cashRegister.isOpen()) {
                        throw new RuntimeException("Cash register is closed");
                }

                return true;
        }

        @Override
        public CashRegisterReportDTO getCashRegisterReport(Long id) {

                CashRegister cashRegister = cashRegisterRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Cash register not found"));

                CashRegisterReportDTO report = new CashRegisterReportDTO();

                report.setCashRegisterId(cashRegister.getId());
                report.setCashRegisterName(cashRegister.getName());
                report.setOpen(cashRegister.isOpen());
                report.setTotalCashSales(cashRegister.getTotalCashSales());
                report.setTotalCardSales(cashRegister.getTotalCardSales());
                report.setTotalSales(cashRegister.getTotalSales());

                return report;
        }
}