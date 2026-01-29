# Copyright (c) 2026 Fatalito
# SPDX-License-Identifier: Apache-2.0

# Global ARG for source selection (builder vs local)
ARG BUILD_SOURCE=builder

# Stage 1: Internal Builder (Self-contained)
FROM eclipse-temurin:25-jdk-alpine AS builder
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
RUN ./gradlew dependencies --no-daemon
COPY src src
RUN ./gradlew bootJar --no-daemon --parallel --configuration-cache

# Stage 2: Local Artifact Injection (For CI speed/Pre-built artifacts)
FROM scratch AS local
WORKDIR /app
COPY build/libs/app.jar /app/build/libs/app.jar

# Stage 3: Source Selector
FROM ${BUILD_SOURCE} AS artifact-source

# Stage 4: Runtime (Hardened)
FROM gcr.io/distroless/java25-debian13:nonroot AS runtime
WORKDIR /app

# Metadata for OCI compliance
LABEL org.opencontainers.image.source="https://github.com/Fatalito/k8s-service-blueprint"
LABEL org.opencontainers.image.description="Hardened Spring Boot 4 service"

# Copy the JAR from the selected source (Internal or Local)
COPY --from=artifact-source /app/build/libs/app.jar app.jar

ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.net.preferIPv4Stack=true"

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]