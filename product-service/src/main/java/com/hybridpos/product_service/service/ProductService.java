package com.hybridpos.product_service.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hybridpos.product_service.dto.ProductCreateDTO;
import com.hybridpos.product_service.dto.ProductPriceUpdateDTO;
import com.hybridpos.product_service.dto.ProductReportDTO;
import com.hybridpos.product_service.dto.ProductResponseDTO;



public interface ProductService{

    ProductResponseDTO createProduct(ProductCreateDTO dto,MultipartFile image);

    ProductResponseDTO getByBarcode(String barcode);
    ProductResponseDTO getById(Long productId);

    List<ProductResponseDTO> getAllProducts();

    void deleteProduct(long productId);

    ProductResponseDTO updatePrice(
            long productId,
            ProductPriceUpdateDTO dto
    );

    void increaseStock(long productId, int amount);
    void decreaseStock(long productId, int amount);
    ProductReportDTO getProductReport();
}
