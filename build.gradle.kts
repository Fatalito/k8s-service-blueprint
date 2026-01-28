import org.cyclonedx.Version

plugins {
    java
    jacoco
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.diffplug.spotless") version "8.2.0"
    id("io.freefair.lombok") version "9.2.0"
    id("org.cyclonedx.bom") version "3.1.0"
}

group = "com.fatalito"
version = "1.0.0-SNAPSHOT"

val javaVersion = project.property("java.version").toString()

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

val normalizeBom =
    tasks.register<Copy>("normalizeBom") {
        from(tasks.named("cyclonedxDirectBom"))
        into(layout.buildDirectory.dir("reports"))
        include("bom.json")
    }
tasks.named("processResources") {
    dependsOn(normalizeBom)
}

// Disable the aggregate task to prevent folder conflicts and redundant work
tasks.named("cyclonedxBom") {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
    // Only show failed tests, --info for all
    testLogging {
        events("failed", "skipped")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

spotless {
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
    java {
        googleJavaFormat("1.33.0").aosp()
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }
}

val installLocalGitHook =
    tasks.register<Copy>("installLocalGitHook") {
        from(file("scripts/pre-commit.sh"))
        into(file(".git/hooks"))
        rename { "pre-commit" }
        filePermissions {
            user {
                read = true
                write = true
                execute = true
            }
            group {
                read = true
                execute = true
            }
            other {
                read = true
                execute = true
            }
        }
    }

// Installs hook
tasks {
    withType<JavaCompile> {
        dependsOn(installLocalGitHook)
    }
    build {
        dependsOn(installLocalGitHook)
    }
}
