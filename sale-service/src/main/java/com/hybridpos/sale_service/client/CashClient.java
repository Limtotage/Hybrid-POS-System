package com.hybridpos.sale_service.client;

import java.math.BigDecimal;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CashClient {

    private final LoadBalancerClient loadBalancerClient;

    private RestClient getRestClient() {

        ServiceInstance instance = loadBalancerClient.choose("CASH-SERVICE");

        if (instance == null) {
            throw new RuntimeException(
                    "CASH-SERVICE Eureka'da bulunamadı.");
        }
        System.out.println("CASH-SERVICE instance: " + instance.getUri().toString());
        return RestClient.builder()
                .baseUrl(instance.getUri().toString())
                .build();
    }

    public void processSale(
            Long cashId,
            BigDecimal cashPaid,
            BigDecimal cardPaid,
            String token) {
        getRestClient().post()
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

        getRestClient().post()
                .uri("/api/cash-registers/{id}/validate-sale", cashId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .toBodilessEntity();
    }
}
