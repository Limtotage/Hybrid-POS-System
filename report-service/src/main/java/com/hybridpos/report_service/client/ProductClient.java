package com.hybridpos.report_service.client;

import com.hybridpos.report_service.dto.StockMovementDTO;
import com.hybridpos.report_service.dto.ProductResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class ProductClient {

        private final RestClient restClient;

        public ProductClient() {
                this.restClient = RestClient.builder()
                                .baseUrl("http://localhost:8082")
                                .build();
        }

        public List<StockMovementDTO> getPurchaseMovements(
                        Long productId,
                        String token) {

                return restClient.get()
                                .uri("/api/products/{id}/stock-movements/purchases", productId)
                                .header(
                                                HttpHeaders.AUTHORIZATION,
                                                "Bearer " + token)
                                .retrieve()
                                .onStatus(
                                                HttpStatusCode::isError,
                                                (request, response) -> {
                                                        throw new RuntimeException(
                                                                        "Product Service error: "
                                                                                        + response.getStatusCode());
                                                })
                                .body(new org.springframework.core.ParameterizedTypeReference<List<StockMovementDTO>>() {
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
	public ProductResponseDTO getProduct(Long productId, String token) {

	    return restClient.get()
	            .uri("/api/products/{id}", productId)
	            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
	            .retrieve()
	            .body(ProductResponseDTO.class);
	}

}
