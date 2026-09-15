package com.hybridpos.product_service.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hybridpos.product_service.entity.Product;



@Repository
public interface  ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findByBarcode(String barcode);
    Optional<Product> findById(Long id);
    List<Product> findByStockQuantityGreaterThan(int quantity);

    boolean existsByBarcode(String barcode);
}
