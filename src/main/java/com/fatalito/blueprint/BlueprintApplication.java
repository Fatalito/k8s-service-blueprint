/*
 * Copyright (c) 2026 Fatalito
 * SPDX-License-Identifier: Apache-2.0
 */

package com.fatalito.blueprint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Core entry point for the Blueprint Service. */
@SpringBootApplication
public class BlueprintApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlueprintApplication.class, args);
    }
}
