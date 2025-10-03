package com.example.WigellTravelService.utils;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class CurrencyConverter {

    private final RestClient restClient;

    public CurrencyConverter(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    public BigDecimal convertSekToEur(BigDecimal sek) {
        Map<String, Object> response = restClient.get()
                .uri("https://v1.apiplugin.io/v1/currency/oHANfmYZ/convert?amount={amount}&from=SEK&to=EUR", sek)
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Object>>() {});

        Object result = response.get("result");

        if ( result == null){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Error in converting SEK to EUR via API");
        }

        return new BigDecimal(result.toString());
    }
}
