package com.example.hybridpos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hybridpos.entity.CashRegister;

public interface CashRegisterRepository extends JpaRepository<CashRegister, Long> {
    
    List<CashRegister> findByShopId(Long shopId);

    Optional<CashRegister> findByIdAndOpenTrue(Long id);

    boolean existsByNameAndShopId(String name, Long shopId);
}
