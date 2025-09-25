package com.example.WigellTravelService.utils;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class CurrencyConverter {

    private final RestClient restClient;

    public CurrencyConverter(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public BigDecimal convertSekToEur(BigDecimal sek) {
        String url = "https://v1.apiplugin.io/v1/currency/oHANfmYZ/convert";
        Map<String, Object> response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .queryParam("amount", sek)
                        .queryParam("from", "SEK")
                        .queryParam("to", "EUR")
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Object>>() {});

        Object result = response.get("result");

        if ( result == null){
            throw new RuntimeException("Error in converting SEK to EUR via API");
        }

        return new BigDecimal(result.toString());
    }
}
