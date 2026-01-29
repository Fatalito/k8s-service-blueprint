/*
 * Copyright (c) 2026 Fatalito
 * SPDX-License-Identifier: Apache-2.0
 */

package com.fatalito.blueprint;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * High-level integration test suite to verify the application's runtime health. *
 *
 * <p>This test ensures that the Spring context initializes correctly and that critical
 * infrastructure endpoints are reachable over a live network port.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class SmokeTest {

    private final WebTestClient webTestClient;

    @Test
    @DisplayName("Context: Application should load and contain main bean")
    void contextLoads(ApplicationContext context) {
        assertThat(context).isNotNull();
        assertThat(context.containsBean("blueprintApplication")).isTrue();
    }

    @Test
    @DisplayName("Integration: Heartbeat should return ALIVE status")
    void healthEndpointIsUp() {
        webTestClient
                .get()
                .uri("/actuator/health")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.status")
                .isEqualTo("UP");
    }
}
