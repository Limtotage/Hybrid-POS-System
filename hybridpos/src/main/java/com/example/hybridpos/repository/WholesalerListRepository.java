package com.example.hybridpos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hybridpos.entity.Shop;
import com.example.hybridpos.entity.WholesalerList;

@Repository
public interface  WholesalerListRepository extends JpaRepository<WholesalerList, Long>{
    Optional<WholesalerList> findTopByShopOrderByCreatedAtDesc(Shop shop);
}
