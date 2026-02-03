package com.example.hybridpos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hybridpos.entity.CashRegister;
import com.example.hybridpos.entity.MyUser;
import com.example.hybridpos.entity.Shop;

@Repository
public interface CashRegisterRepository extends JpaRepository<CashRegister, Long> {

    boolean existsByNameAndOpenTrue(String name);

    boolean existsByCashierAndOpenTrue(MyUser cashier);
    List<CashRegister> findByShop(Shop shop);
    

    Optional<CashRegister> findByIdAndOpenTrue(Long id);

    boolean existsByNameAndShopId(String name, Long shopId);
}
