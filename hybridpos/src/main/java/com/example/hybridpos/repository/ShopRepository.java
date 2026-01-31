package com.example.hybridpos.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hybridpos.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop,Long>{
    boolean existsByName(String name);
    List<Shop> getAll();
}
