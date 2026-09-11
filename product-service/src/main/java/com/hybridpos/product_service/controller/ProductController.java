package com.hybridpos.product_service.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hybridpos.product_service.dto.ProductCreateDTO;
import com.hybridpos.product_service.dto.ProductPriceUpdateDTO;
import com.hybridpos.product_service.dto.ProductResponseDTO;
import com.hybridpos.product_service.dto.StockUpdateDTO;
import com.hybridpos.product_service.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

        private final ProductService productService;

        @PreAuthorize("hasRole('ADMIN')")
        @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<ProductResponseDTO> addProduct(
                        @RequestPart("product") ProductCreateDTO dto,
                        @RequestPart(value = "image", required = false) MultipartFile image) throws IOException{

                return ResponseEntity.ok(
                                productService.createProduct(dto, image));
        }

        @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteProduct(
                        @PathVariable Long id) {

                productService.deleteProduct(id);

                return ResponseEntity.noContent().build();
        }

        @PreAuthorize("hasRole('ADMIN')")
        @PutMapping("/{id}/price")
        public ResponseEntity<ProductResponseDTO> updatePrice(
                        @PathVariable Long id,
                        @RequestBody ProductPriceUpdateDTO dto) {

                return ResponseEntity.ok(
                                productService.updatePrice(id, dto));
        }

        @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
        @PostMapping("/{id}/stock")
        public ResponseEntity<Void> increaseStock(
                        @PathVariable Long id,
                        @RequestBody StockUpdateDTO dto) {
                           System.out.println("SomeOne Trying to access here");     
                productService.increaseStock(
                                id,
                                dto.getAmount());

                return ResponseEntity.ok().build();
        }

        @PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
        @GetMapping
        public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {

                return ResponseEntity.ok(
                                productService.getAllProducts());
        }

        @PreAuthorize("hasAnyRole('ADMIN','CASHIER')")
        @GetMapping("/barcode/{barcode}")
        public ResponseEntity<ProductResponseDTO> getByBarcode(
                        @PathVariable String barcode) {

                return ResponseEntity.ok(
                                productService.getByBarcode(barcode));
        }
}
