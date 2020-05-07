package com.example.demo;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.cloud.contract.wiremock.WireMockConfigurationCustomizer;
import org.springframework.stereotype.Component;

@Component
public class DummyWireMockConfigurationCustomizer implements WireMockConfigurationCustomizer {
    @Override
    public void customize(WireMockConfiguration config) {
        config.extensions(DummyResponseTransformer.class);
    }
}
