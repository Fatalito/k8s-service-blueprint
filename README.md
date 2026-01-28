# Enterprise Kubernetes Service Blueprint

This repository is an enterprise-grade reference architecture for building, deploying, and operating highly available microservices on **AWS EKS**. 

## 📦 What’s Included
- **Java 25 / Spring Boot 4.0.x:** Following Clean Architecture and SOLID principles.
- **Infrastructure as Code:** Terraform modules for VPC, EKS (Managed Node Groups), and IAM Roles for Service Accounts (IRSA).
- **Orchestration:** Helm v3 charts featuring Pod Disruption Budgets (PDB), HPA, and Resource Quotas.
- **CI/CD Pipelines:** GitHub Actions for automated testing, Snyk security scanning, and SBOM generation.
- **Observability:** OpenTelemetry integration with Prometheus/Grafana alerting rules.

## 🏗 Architectural Highlights
- **Clean Architecture:** Separation of concerns between transport, domain logic, and data persistence.
- **GitOps Ready:** Infrastructure defined via Terraform and deployed via GitHub Actions.
- **Observability:** Pre-configured for Prometheus metrics and structured JSON logging.

## 🛠 Tech Stack
- **Runtime:** Java/Spring Boot
- **Infrastructure:** AWS EKS, Terraform
- **Orchestration:** Kubernetes (Helm)
- **CI/CD:** GitHub Actions

## 🛡 Security & Resilience
- **Multi-stage Docker builds** to minimize attack surface.
- **Kubernetes Pod Disruption Budgets** and resource quotas.
- **Automated Snyk scanning** in the CI pipeline.

## 🛠️ Development Environment
This project uses **AOSP (Google) Code Style**.
- **VS Code:** Recommended extensions and settings are provided in `.vscode/`. Formatting will happen automatically on save.
- **IntelliJ:** Install the 'Google Java Format' plugin and enable 'AOSP' style.
- **Manual:** Run `./gradlew spotlessApply` before committing.

## 🚀 Quick Start

### Build and Test
```bash
./gradlew clean build
```

### To fix style
```bash
./gradlew spotlessApply 
```
