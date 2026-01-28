/*
 * Copyright (c) 2026 Fatalito
 * SPDX-License-Identifier: Apache-2.0
 */

package com.fatalito.blueprint.infrastructure.web;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Basic controller to verify the web adapter is responding. */
@RestController
public class HelloController {

    @GetMapping("/v1/hello")
    public Map<String, String> getHello() {
        return Map.of("hello", "world");
    }
}
