package com.hybridpos.report_service.client;

import com.hybridpos.report_service.dto.StockMovementDTO;

import lombok.RequiredArgsConstructor;

import com.hybridpos.report_service.dto.ProductResponseDTO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;

@Component
@RequiredArgsConstructor 
public class ProductClient {

        private final LoadBalancerClient loadBalancerClient;

        private RestClient getRestClient() {

                ServiceInstance instance = loadBalancerClient.choose("PRODUCT-SERVICE");

                if (instance == null) {
                        throw new RuntimeException(
                                        "PRODUCT-SERVICE Eureka'da bulunamadı.");
                }

                return RestClient.builder()
                                .baseUrl(instance.getUri().toString())
                                .build();
        }

        public List<StockMovementDTO> getPurchaseMovements(
                        Long productId,
                        String token) {

                return getRestClient()
                                .get()
                                .uri("/api/products/{id}/stock-movements/purchases", productId)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .retrieve()
                                .body(new ParameterizedTypeReference<List<StockMovementDTO>>() {
                                });
        }

        public StockMovementDTO getLatestPurchaseMovement(
                        Long productId,
                        String token) {

                List<StockMovementDTO> movements = getPurchaseMovements(productId, token);

                return movements.stream()
                                .max((a, b) -> a.getCreatedAt().compareTo(b.getCreatedAt()))
                                .orElse(null);
        }

        public ProductResponseDTO getProduct(
                        Long productId,
                        String token) {

                return getRestClient()
                                .get()
                                .uri("/api/products/{id}", productId)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .retrieve()
                                .body(ProductResponseDTO.class);
        }

        public List<ProductResponseDTO> getAllProducts(
                        String token) {

                return getRestClient()
                                .get()
                                .uri("/api/products")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                .retrieve()
                                .body(new ParameterizedTypeReference<List<ProductResponseDTO>>() {
                                });
        }
}
