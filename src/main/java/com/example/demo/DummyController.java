package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(path = "/test")
public class DummyController {

    private final DummyService dummyService;

    @GetMapping(path = "/get/{id}")
    public Mono<ResponseEntity<String>> ping(@PathVariable("id") final String id) {
        return dummyService.pingById(id)
                .map(updatedId -> {
                    log.info("get id from remote server: {}", updatedId);
                    return ResponseEntity.ok(updatedId);
                });
    }

    @PostMapping(path = "/post")
    public Mono<ResponseEntity<String>> submit(@RequestBody final String requestBody) {
        return dummyService.pingWithBody(requestBody)
                .map(res -> {
                    log.info("response from remote server: {}", res);
                    return ResponseEntity.ok(res);
                });
    }

    @PostMapping(path = "/transform")
    public Mono<ResponseEntity<String>> transform(@RequestBody final String requestBody) {
        return dummyService.transform(requestBody)
                .map(res -> {
                    log.info("response from remote server: {}", res);
                    return ResponseEntity.ok(res);
                });
    }
}
