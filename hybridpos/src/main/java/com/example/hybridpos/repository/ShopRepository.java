package com.example.hybridpos.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hybridpos.entity.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long>{
    boolean existsByName(String name);
}
