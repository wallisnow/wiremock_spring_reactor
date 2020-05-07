package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@AutoConfigureWireMock(port = 0)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DummyControllerTest {

    private WebTestClient webTestClient;

    @LocalServerPort
    private int port;

    @BeforeAll
    void setup() {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .responseTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Test
    void testPing() {
        stubFor(get(urlMatching("/remote/.*"))
                .willReturn(aResponse()
                        .withBody("123")
                        .withStatus(200)));

        webTestClient
                .get()
                .uri("/test/get/{id}", 123)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    Assertions.assertEquals("123", stringEntityExchangeResult.getResponseBody());
                });

    }

    @Test
    void testSubmit() {
        String requestBody = "{\n" +
                "    \"action\":\"post\",\n" +
                "    \"value\":\"wiremock\"\n" +
                "}";

        stubFor(post(urlMatching("/remote/post"))
                .willReturn(aResponse()
                        .withBody(requestBody)
                        .withStatus(200)));

        webTestClient
                .post()
                .uri("/test/post")
                .bodyValue("test")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    Assertions.assertEquals(requestBody, stringEntityExchangeResult.getResponseBody());
                });

    }

    @Test
    void testTransformer() {

        stubFor(post(urlEqualTo("/change/id")).willReturn(aResponse()
                .withTransformers(DummyResponseTransformer.DUMMY_TRANSFORMER)
                .withTransformerParameter("prefix", "Hello_")));

        webTestClient
                .post()
                .uri("/test/transform")
                .bodyValue("666")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    String responseBody = stringEntityExchangeResult.getResponseBody();
                    log.info(responseBody);
                    Assertions.assertEquals(responseBody, "{\"id\":\"Hello_666\"}");
                });

    }
}