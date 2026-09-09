package com.example.hybridpos.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hybridpos.dto.ProductCreateDTO;
import com.example.hybridpos.dto.ProductPriceUpdateDTO;
import com.example.hybridpos.dto.ProductResponseDTO;
import com.example.hybridpos.dto.StockUpdateDTO;
import com.example.hybridpos.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

        private final ProductService productService;

        @PreAuthorize("hasRole('ADMIN')")
        @PostMapping
        public ResponseEntity<ProductResponseDTO> addProduct(
                        @RequestBody ProductCreateDTO dto) {

                return ResponseEntity.ok(
                                productService.createProduct(dto));
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

        @PreAuthorize("hasRole('ADMIN')")
        @PostMapping("/{id}/stock")
        public ResponseEntity<Void> increaseStock(
                        @PathVariable Long id,
                        @RequestBody StockUpdateDTO dto) {

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
