package com.hybridpos.sale_service.client;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductClient {

    private final RestClient restClient;

    public ProductClient(@Qualifier("productRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public ProductResponse getProductByBarcode(
            String barcode,
            String token) {

        return restClient.get()
                .uri("/api/products/barcode/{barcode}", barcode)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(ProductResponse.class);
    }

    public static class ProductResponse {

        private Long id;
        private String barcode;
        private String name;
        private BigDecimal purchasePrice;
        private BigDecimal salePrice;
        private int stockQuantity;
        private boolean active;
        private String imageUrl;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getPurchasePrice() {
            return purchasePrice;
        }

        public void setPurchasePrice(BigDecimal purchasePrice) {
            this.purchasePrice = purchasePrice;
        }

        public BigDecimal getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(BigDecimal salePrice) {
            this.salePrice = salePrice;
        }

        public int getStockQuantity() {
            return stockQuantity;
        }

        public void setStockQuantity(int stockQuantity) {
            this.stockQuantity = stockQuantity;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public void updateStock(
            Long productId,
            int amount,
            String token) {
        restClient.post()
                .uri("/api/products/{id}/stock", productId)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body("""
                        {
                            "amount": %d
                        }
                        """.formatted(amount))
                .retrieve()
                .toBodilessEntity();
    }

    public void sale(
            Long productId,
            int amount,
            String token) {
                System.err.println("ProductClient.sale called with productId: " + productId + ", amount: " + amount + ", token: " + token);
        restClient.post()
                .uri("/api/products/{id}/sale", productId)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body("""
                        {
                            "amount": %d
                        }
                        """.formatted(amount))
                .retrieve()
                .toBodilessEntity();
    }
}