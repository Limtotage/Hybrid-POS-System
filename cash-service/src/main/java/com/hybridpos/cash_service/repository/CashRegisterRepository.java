package com.hybridpos.cash_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hybridpos.cash_service.entity.CashRegister;


@Repository
public interface CashRegisterRepository extends JpaRepository<CashRegister, Long> {

}
