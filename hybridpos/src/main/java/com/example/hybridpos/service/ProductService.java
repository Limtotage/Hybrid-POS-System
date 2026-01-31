package com.example.hybridpos.interfaces;

import java.util.List;

import com.example.hybridpos.dto.ProductCreateDTO;
import com.example.hybridpos.dto.ProductPriceUpdateDTO;
import com.example.hybridpos.dto.ProductResponseDTO;

public interface ProductInterface {
    ProductResponseDTO createProduct(Long shopId, ProductCreateDTO dto);

    ProductResponseDTO getByBarcode(String barcode);
    List<ProductResponseDTO> getAllProducts(Long shopId);

    void deleteProduct(Long productId);

    ProductResponseDTO updatePrice(Long productId, ProductPriceUpdateDTO dto);

    void increaseStock(String barcode, int amount);
}
