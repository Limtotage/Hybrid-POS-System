package com.example.hybridpos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.hybridpos.entity.Product;
import com.example.hybridpos.entity.Shop;

@Repository
public interface  ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findByBarcode(String barcode);
    List<Product> findByShop(Shop shop);

    List<Product> findByShopId(Long shopId);

    List<Product> findByStockQuantityGreaterThan(int quantity);

    boolean existsByBarcode(String barcode);
}
