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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hybridpos.dto.ProductCreateDTO;
import com.example.hybridpos.dto.ProductPriceUpdateDTO;
import com.example.hybridpos.dto.ProductResponseDTO;
import com.example.hybridpos.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // OWNER: ürün ekleme
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/{shopid}")
    public ResponseEntity<ProductResponseDTO> addProduct(@PathVariable Long shopid, @RequestBody ProductCreateDTO dto) {
        return ResponseEntity.ok(productService.createProduct(shopid, dto));
    }

    // OWNER: ürün silme
    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // OWNER: zam / indirim
    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/{id}/price")
    public ResponseEntity<ProductResponseDTO> updatePrice(
            @PathVariable Long id,
            @RequestParam ProductPriceUpdateDTO newPrice) {
        return ResponseEntity.ok(productService.updatePrice(id, newPrice));
    }

    // CASHIER + OWNER: ürün görüntüleme
    @PreAuthorize("hasAnyRole('OWNER','CASHIER')")
    @GetMapping("/{shopId}")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(@PathVariable Long shopId) {
        return ResponseEntity.ok(productService.getAllProducts(shopId));
    }

    // Barkod ile ürün bul
    @PreAuthorize("hasAnyRole('OWNER','CASHIER')")
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<ProductResponseDTO> getByBarcode(@PathVariable String barcode) {
        return ResponseEntity.ok(productService.getByBarcode(barcode));
    }
}
