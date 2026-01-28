# Engineering Standards & AI Governance

**Scope:** All service modules, infrastructure code, and Pull Requests targeting `main`.

---

## 🏛️ Architecture & Clean Code
- **Core Principles:** Strictly adhere to **SOLID** and **Separation of Concerns (SoC)**. 
- **Pattern:** Follow **Hexagonal / Clean Architecture**. Domain logic must be pure, framework-agnostic, and isolated from external side effects (DB, API, Spring context).
- **Dependency Injection:** Use **Constructor Injection** (Lombok `@RequiredArgsConstructor` is approved).
- **Data Models:** Prefer **Java Records** for DTOs and immutable Domain Entities.
- **Style:** Adhere to **google-java-format** AOSP. 

## 🤖 AI Code Generation Guardrails
- **TDD Workflow:** Always generate Unit Tests before or alongside implementation.
- **Documentation:** Provide class-level Javadoc explaining the "Why" (intent), not just the "What."
- **Self-Documenting Code:** Prioritize expressive naming over excessive commenting.
- **Security Flags:** Any AI-generated code involving Cryptography, Authentication, or Persistence must include a `// TODO: Security Review` comment for manual audit.

## 🧪 Testing & Quality Thresholds
- **Coverage:** Minimum **80% line coverage** required for business logic.
- **Requirements:** Every feature must include at least one Unit Test and one "Happy Path" Integration Test (using `@SpringBootTest` or `Testcontainers`).
- **Standard:** Use **JUnit 5** and **Mockito**.

## 🛡️ Security & Supply Chain
- **Secrets:** Zero-tolerance for hardcoded credentials. Use placeholders for **AWS Secrets Manager**.
- **Dependencies:** Use stable Maven Central releases only (No Snapshots). New libraries require a comment block justifying the necessity and security trade-off.
- **Compliance:** Every build must generate a **CycloneDX SBOM**. High/Critical CVEs must be patched within 48 hours.
- **Licensing:** Include standard Apache 2.0 / MIT headers in all new files.
