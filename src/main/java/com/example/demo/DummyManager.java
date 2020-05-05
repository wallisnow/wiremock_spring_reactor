package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@AllArgsConstructor
@ConfigurationPropertiesScan
public class DummyManager {

    private final DummyConfiguration dummyConfiguration;

    @Bean
    WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(dummyConfiguration.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
