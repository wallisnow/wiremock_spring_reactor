package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class DummyService {

    private final WebClient webClient;

    private static final String REMOTE_GET_PATH = "/remote/get/{id}";
    private static final String REMOTE_POST_PATH = "/remote/post";

    public Mono<String> pingById(String id) {
        log.info("processing id, {}", id);
        return webClient.get()
                .uri(REMOTE_GET_PATH, id)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> pingWithBody(String bodyValue) {
        log.info("processing bodyValue, {}", bodyValue);
        return webClient.post()
                .uri(REMOTE_POST_PATH)
                .bodyValue(bodyValue)
                .retrieve()
                .bodyToMono(String.class);
    }
}
