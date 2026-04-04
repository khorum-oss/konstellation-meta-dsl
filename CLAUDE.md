# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Published annotation and utility artifact for the Konstellation KSP-based DSL code generator. This is a **standalone Gradle project** (no parent `settings.gradle.kts`) providing the compile-time annotations and runtime helpers that consumers use alongside the `konstellation-dsl` processor.

- **Group:** `org.khorum.oss.konstellation`
- **Package:** `io.violabs.konstellation.metaDsl`
- **Kotlin:** 2.1.20, **Java:** 21
- **Version:** tracked in `VERSION` file (semver)

## Build Commands

```bash
./gradlew build          # compile + test
./gradlew test           # unit tests only (JUnit 5)
./gradlew detekt         # static analysis
./gradlew koverXmlReport # coverage report
./gradlew dokkaJavadoc   # generate KDoc
```

Run a single test class:
```bash
./gradlew test --tests "io.violabs.konstellation.metaDsl.SomeTest"
```

Debug KSP logging: `./gradlew clean build -Ddebug=true`

## Publishing

Artifacts are published to DigitalOcean Spaces (`open-reliquary` bucket). Requires secrets: `DO_SPACES_API_KEY`, `DO_SPACES_SECRET`, `GPG_SIGNING_KEY`, `GPG_SIGNING_PASSWORD`.

```bash
./gradlew uploadToDigitalOceanSpaces
```

GPG signing is required by default. Key is read from `khorum-signing.asc` at project root or `GPG_SIGNING_KEY` env var.

## Architecture

The codebase has two main areas:

**Annotations** (`annotation/`): Processed by the `konstellation-dsl` KSP processor to generate builder classes.
- `@GeneratedDsl` — marks data classes for DSL generation; options: `withListGroup`, `withMapGroup`, `isRoot`, `debug`
- `@DslProperty` — controls vararg/provider function generation for list/map properties
- `@DefaultValue` — specifies default values with package/class metadata
- `@SingleEntryTransformDsl` — enables single-input transform functions (e.g., `Duration(value)`)
- `MapGroupType` enum — NONE, SINGLE, LIST, ALL

**Runtime utilities**: Used by generated code at runtime.
- `CoreDslBuilder<T>` — interface all generated builders implement (`build(): T`)
- `RequiredValidation.kt` — `vRequireNotNull`, `vRequireCollectionNotEmpty`, `vRequireMapNotEmpty` functions using Kotlin reflection to validate builder state

## Dependency Verification

This project uses `gradle/verification-metadata.xml` with both checksum and PGP signature verification. When adding or updating dependencies:
```bash
./gradlew --write-verification-metadata sha256,pgp help
```

## CI/CD

Standard two-workflow pattern:
- **`pr-main.yml`**: build → detekt → unit tests (90% coverage min) → Codecov + SonarCloud → Discord notification
- **`merge-main.yml`**: version bump → publish to DO Spaces → GitHub release → Discord notification
