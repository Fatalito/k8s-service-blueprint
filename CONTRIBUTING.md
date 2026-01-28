# Contributing Guide

## Development Workflow
1. **Branching:** Use `feature/` or `fix/` prefixes for branches.
2. **Quality Gates:** All PRs must pass the GitHub Actions suite (Unit tests, Linting, Snyk scan).
3. **Standards:** Follow the Hexagonal Architecture guidelines in `.github/copilot-instructions.md`.

## Commit Messages
We follow [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/).

## Tooling & Version Pinning
To ensure build reproducibility, the following versions are enforced:
- **Runtime:** Java 25 (LTS) / Spring Boot 4.0.1
- **IaC:** Terraform v1.14.x / AWS Provider v6.x
- **Linter:** google-java-format v1.33.0
- **Container:** Alpine-based JRE (Eclipse Temurin)
