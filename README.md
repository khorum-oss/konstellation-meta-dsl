# Konstellation Meta DSL

[![CI](https://github.com/khorum-oss/konstellation-metal-dsl/actions/workflows/pr-main.yml/badge.svg)](https://github.com/khorum-oss/konstellation-metal-dsl/actions/workflows/pr-main.yml)
[![codecov](https://codecov.io/github/khorum-oss/konstellation-meta-dsl/graph/badge.svg?token=77u8b5h0WV)](https://codecov.io/github/khorum-oss/konstellation-meta-dsl)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=khorum-oss_konstellation-meta-dsl&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=khorum-oss_konstellation-meta-dsl)
[![CodeQL](https://github.com/khorum-oss/konstellation-metal-dsl/actions/workflows/code-ql.yml/badge.svg)](https://github.com/khorum-oss/konstellation-metal-dsl/actions/workflows/code-ql.yml)

A Kotlin Symbol Processing (KSP) library for automatically generating type-safe Kotlin DSLs from annotated data classes.

## Installation

Add the repository and dependency to your `build.gradle.kts`:

```kotlin
repositories {
    mavenCentral()
    maven {
        url = uri("https://open-reliquary.nyc3.cdn.digitaloceanspaces.com")
    }
}

dependencies {
    implementation("org.khorum.oss.konstellation:konstellation-meta-dsl:1.0.0")
}
```

## Usage

### 1. Configure KSP

```kotlin
plugins {
    id("com.google.devtools.ksp") version "2.1.20-1.0.32"
}

ksp {
    arg("projectRootClasspath", "com.yourcompany.yourproject")
    arg("dslBuilderClasspath", "com.yourcompany.yourproject.builders")
    arg("dslMarkerClass", "com.yourcompany.yourproject.YourDSL")
}

kotlin.sourceSets["main"].kotlin {
    srcDir("build/generated/ksp/main/kotlin")
}
```

### 2. Annotate Your Classes

```kotlin
@GenerateDSL
data class DatabaseConfig(
    val host: String,
    val port: Int,
    val database: String,
    val ssl: Boolean = false
)
```

### 3. Use Your Generated DSL

```kotlin
val config = databaseConfig {
    host = "localhost"
    port = 5432
    database = "myapp"
    ssl = true
}
```

## Nested Configuration

```kotlin
@GenerateDSL
data class BloomBuildPlannerQueue(
    val maxQueuedTasksPerTenant: Int,
    val storeTasksOnDisk: Boolean,
    val tasksDiskDirectory: String,
    val cleanTasksDirectory: Boolean
)

@GenerateDSL
data class ServiceConfig(
    val name: String,
    val queue: BloomBuildPlannerQueue,
    val timeout: Duration = Duration.ofSeconds(30)
)
```

Generated usage:

```kotlin
val service = serviceConfig {
    name = "data-processor"
    queue {
        maxQueuedTasksPerTenant = 100
        storeTasksOnDisk = true
        tasksDiskDirectory = "/tmp/tasks"
        cleanTasksDirectory = true
    }
    timeout = Duration.ofMinutes(5)
}
```

## Configuration Options

| KSP Argument | Description | Example |
|--------------|-------------|---------|
| `projectRootClasspath` | Root package for your project | `com.yourcompany.project` |
| `dslBuilderClasspath` | Package where builders are generated | `com.yourcompany.project.dsl` |
| `dslMarkerClass` | Custom DSL marker annotation class | `com.yourcompany.project.MyDSL` |

## Development & Debugging

Run with debug logging: `./gradlew clean build -Ddebug=true`

To enable debug logging in your KSP processor:

```kotlin
ksp {
    arg("enableDebugLogging", "true")
}
```

## License

Apache License 2.0
