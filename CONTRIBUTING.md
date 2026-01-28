# Contributing Guide

## 🏗 Architecture & Standards
We follow Hexagonal Architecture and Java 25 idioms. 
- **Specifications:** Review [copilot-instructions.md](.github/copilot-instructions.md) for layering rules, TDD requirements, and security protocols.

## Development Workflow
- **Environment:** Run `sdk env` to use the pinned JDK version in `.sdkmanrc`.
- **Branching:** Use `feature/` or `fix/` prefixes for branches.
- **Git Hooks:** Pre-commit hooks are installed automatically on your first `./gradlew build`. 
  - The hook runs `spotlessApply` and `testClasses`. 
  - **Auto-Fix:** Formatting issues are corrected and re-staged automatically.
  - **Validation:** Only verify that code and tests compile.
- **Commits:** Use [Conventional Commits](https://www.conventionalcommits.org/).

## 🧪 Testing & Quality
- **Domain:** Business logic requires Unit Tests (JUnit 5).
- **Infrastructure:** Use Testcontainers for integration tests.
- **Gate:** CI enforces an 80% coverage floor. See PR comments for Jacoco reports.

## 🛡 Security
Report vulnerabilities privately. See [SECURITY.md](SECURITY.md) for the disclosure process.

## Tooling & Version Pinning
To ensure build reproducibility, the following versions are enforced:
- **Runtime:** Java 25 (LTS) / Spring Boot 4.0.1
- **IaC:** Terraform v1.14.x / AWS Provider v6.x
- **Linter:** google-java-format v1.33.0 (AOSP)
- **Container:** Distroless (gcr.io/distroless/java25)
