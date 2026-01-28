// Copyright (c) 2026 Fatalito
// SPDX-License-Identifier: Apache-2.0

plugins {
    java
    jacoco
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spotless)
    alias(libs.plugins.cycloneDx)
    alias(libs.plugins.lombok)
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
    implementation(platform(libs.spring.boot.dependencies))

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.micrometer.registry.prometheus)

    testImplementation(libs.spring.boot.starter.test)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.build {
    dependsOn(tasks.cyclonedxDirectBom)
}

tasks.cyclonedxDirectBom {
    xmlOutput.unsetConvention()
    jsonOutput.set(layout.buildDirectory.file("reports/${project.name}-bom.json"))
    includeConfigs.set(listOf("runtimeClasspath"))
}
tasks.cyclonedxBom {
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

val rawLicense = file("license.header").readLines()

fun comment(
    lines: List<String>,
    prefix: String,
    suffix: String = "",
    linePrefix: String = "",
): String {
    val content = lines.joinToString("\n") { "$linePrefix$it" }
    return "$prefix\n$content\n$suffix\n\n".replace(Regex("\n{3,}"), "\n\n").trimStart()
}
spotless {
    kotlinGradle {
        target("*.gradle.kts", "gradle/*.gradle.kts")
        ktlint()
        licenseHeader(comment(rawLicense, "", "", "// "), "^(import |plugins |rootProject|include)")
    }
    java {
        target("src/*/java/**/*.java")
        googleJavaFormat("1.33.0").aosp()
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
        licenseHeader(comment(rawLicense, "/*", " */", " * "), "^package ")
    }
    format("styling") {
        target("**/*.yml", "**/*.yaml", "**/*.properties", "**/*.toml", ".env")
        targetExclude("**/build/**", "**/.gradle/**")
        licenseHeader(comment(rawLicense, "", "", "# "), "^[^#\\s]")
    }
}

val installLocalGitHook =
    tasks.register<Copy>("installLocalGitHook") {
        val gitHooksDir = layout.projectDirectory.dir(".git/hooks")

        onlyIf { gitHooksDir.asFile.parentFile.exists() }

        from(layout.projectDirectory.file("scripts/pre-commit.sh"))
        into(gitHooksDir)
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

// Install hook
tasks {
    withType<JavaCompile> {
        dependsOn(installLocalGitHook)
    }
    build {
        dependsOn(installLocalGitHook)
    }
}
