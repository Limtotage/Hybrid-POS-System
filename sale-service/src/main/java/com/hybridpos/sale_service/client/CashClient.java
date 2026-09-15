package com.hybridpos.sale_service.client;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CashClient {

    private final RestClient restClient;

    public CashClient(@Qualifier("cashRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public void processSale(
            Long cashId,
            BigDecimal cashPaid,
            BigDecimal cardPaid,
            String token) {
        restClient.post()
                .uri("/api/cash-registers/{id}/sale", cashId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .body("""
                        {
                            "cashPaid": %s,
                            "cardPaid": %s
                        }
                        """.formatted(
                        cashPaid,
                        cardPaid))
                .retrieve()
                .toBodilessEntity();
    }

    public void validateSale(
            Long cashId,
            String token) {

        restClient.post()
                .uri("/api/cash-registers/{id}/validate-sale", cashId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity();
    }
}
