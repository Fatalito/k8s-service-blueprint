# Copyright (c) 2026 Fatalito
# SPDX-License-Identifier: Apache-2.0

# Stage 1: Build (Optional for CI, used for local development)
FROM eclipse-temurin:25-jdk-alpine AS build
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
RUN ./gradlew dependencies --no-daemon
COPY src src
RUN ./gradlew build --no-daemon --parallel --configuration-cache

# Stage 2: Runtime (Hardened)
FROM gcr.io/distroless/java25-debian12 AS runtime
WORKDIR /app

# Metadata for OCI compliance
LABEL org.opencontainers.image.source="https://github.com/Fatalito/k8s-service-blueprint"
LABEL org.opencontainers.image.description="Hardened Spring Boot 4 service"

# Allow passing a JAR file directly to speed up CI builds
ARG JAR_FILE=build/libs/app.jar
COPY ${JAR_FILE} app.jar

# JVM Configuration via environment variables (Overridable in K8s)
# - UseContainerSupport: Auto-scales JVM memory to container limits
# - MaxRAMPercentage: Leaves 25% for OS/Metaspace
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.net.preferIPv4Stack=true"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]