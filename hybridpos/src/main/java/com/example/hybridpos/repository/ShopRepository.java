package com.example.hybridpos.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hybridpos.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop,Long>{
    boolean existsByName(String name);
}
