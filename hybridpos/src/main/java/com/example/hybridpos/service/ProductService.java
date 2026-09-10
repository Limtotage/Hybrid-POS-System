package com.example.hybridpos.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.hybridpos.dto.ProductCreateDTO;
import com.example.hybridpos.dto.ProductPriceUpdateDTO;
import com.example.hybridpos.dto.ProductResponseDTO;

public interface ProductService{

    ProductResponseDTO createProduct(ProductCreateDTO dto,MultipartFile image);

    ProductResponseDTO getByBarcode(String barcode);

    List<ProductResponseDTO> getAllProducts();

    void deleteProduct(long productId);

    ProductResponseDTO updatePrice(
            long productId,
            ProductPriceUpdateDTO dto
    );

    void increaseStock(long productId, int amount);
}
