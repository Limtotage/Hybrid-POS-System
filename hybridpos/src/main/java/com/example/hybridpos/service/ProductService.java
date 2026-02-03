package com.example.hybridpos.service;

import java.util.List;

import com.example.hybridpos.dto.ProductCreateDTO;
import com.example.hybridpos.dto.ProductPriceUpdateDTO;
import com.example.hybridpos.dto.ProductResponseDTO;

public interface ProductService{
    ProductResponseDTO createProduct(long shopId, ProductCreateDTO dto);

    ProductResponseDTO getByBarcode(String barcode);
    List<ProductResponseDTO> getAllProducts(long shopId);

    void deleteProduct(long productId);

    ProductResponseDTO updatePrice(long productId, ProductPriceUpdateDTO dto);

    void increaseStock(String barcode, int amount);
}
