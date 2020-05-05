package com.example.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@ConfigurationProperties("myserver")
@Validated
@Data
public class DummyConfiguration {
    @NotNull
    private String baseUrl;
}
