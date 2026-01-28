// Copyright (c) 2026 Fatalito
// SPDX-License-Identifier: Apache-2.0

rootProject.name = "k8s-service-blueprint"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
}
