/*
 * Copyright (c) 2026 Fatalito
 * SPDX-License-Identifier: Apache-2.0
 */

package com.fatalito.blueprint.infrastructure.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired private MockMvc mockMvc;

    @Test
    void shouldReturnAliveStatus() throws Exception {
        mockMvc.perform(get("/v1/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hello").value("world"));
    }
}
