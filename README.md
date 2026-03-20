# Konstellation Meta DSL

[![CI](https://github.com/khorum-oss/konstellation-metal-dsl/actions/workflows/pr-main.yml/badge.svg)](https://github.com/khorum-oss/konstellation-metal-dsl/actions/workflows/pr-main.yml)
[![codecov](https://codecov.io/github/khorum-oss/konstellation-meta-dsl/graph/badge.svg?token=77u8b5h0WV)](https://codecov.io/github/khorum-oss/konstellation-meta-dsl)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=khorum-oss_konstellation-meta-dsl&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=khorum-oss_konstellation-meta-dsl)
[![CodeQL](https://github.com/khorum-oss/konstellation-metal-dsl/actions/workflows/code-ql.yml/badge.svg)](https://github.com/khorum-oss/konstellation-metal-dsl/actions/workflows/code-ql.yml)

A Kotlin Symbol Processing (KSP) library for automatically generating type-safe Kotlin DSLs from annotated data classes.

## Installation

> NOTE! This library is currently in beta and is subject to change. It is not available for konstellation-dsl:1.0.2
> Please see the [konstellation-dsl](https://github.com/khorum-oss/konstellation-dsl) repository for the stable version
> and what annotations are available.

aAdd the repository and dependency to your `build.gradle.kts`:

```kotlin
repositories {
    mavenCentral()
    maven {
        url = uri("https://open-reliquary.nyc3.cdn.digitaloceanspaces.com")
    }
}

dependencies {
    implementation("org.khorum.oss.konstellation:konstellation-meta-dsl:1.0.1")
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
@GeneratedDsl
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

## Annotations

### Class-Level Annotations

#### `@GeneratedDsl`

Marks a data class for DSL generation.

| Parameter       | Type      | Default      | Description                                                             |
|-----------------|-----------|--------------|-------------------------------------------------------------------------|
| `withListGroup` | `Boolean` | `false`      | Enable list group generation - DEPRECATED use ListDsl                   |
| `withMapGroup`  | `String`  | `"NONE"`     | Map group type: `NONE`, `SINGLE`, `LIST`, `ALL` - DEPRECATED use MapDsl |
| `isRoot`        | `Boolean` | `false`      | Mark as root DSL entry point - DEPRECATED use RootDsl                   |
| `name`          | `String`  | `class name` | A way to override the class name                                        |
| `debug`         | `Boolean` | `false`      | Enable debug logging during KSP processing                              |

#### `@DslDescription`

Adds KDoc documentation to generated builder classes and accessor functions. Applied to classes or properties.

| Parameter | Type     | Default    | Description                          |
|-----------|----------|------------|--------------------------------------|
| `value`   | `String` | *required* | KDoc text attached to generated code |

### Property-Level Annotations

#### `@RootDsl`

Marks a property as the root DSL entry point. Replaces `@GeneratedDsl(isRoot = true)`.

| Parameter | Type     | Default | Description                                                  |
|-----------|----------|---------|--------------------------------------------------------------|
| `name`    | `String` | `""`    | Name of the root DSL object (defaults to class name)         |
| `alias`   | `String` | `""`    | Optional alias for the root DSL object (creates a separate builder) |

#### `@PublicDslProperty`

Controls generated accessor behavior for public properties.

| Parameter        | Type      | Default | Description                                                      |
|------------------|-----------|---------|------------------------------------------------------------------|
| `restrictSetter` | `Boolean` | `false` | When `true`, disables the direct setter                          |
| `wrapInFunction` | `Boolean` | `false` | When `true`, generates an additional function-based accessor     |

```kotlin
@GeneratedDsl
data class Person(
    val name: String,                                  // default: setter only

    @PublicDslProperty
    val age: Int,                                      // normal interaction

    @PublicDslProperty(restrictSetter = true)
    val position: Int = 0,                             // disables setter

    @PublicDslProperty(wrapInFunction = true)
    val amount: Int,                                   // setter + function

    @PublicDslProperty(wrapInFunction = true, restrictSetter = true)
    val address: String                                // function only
)
```

#### `@PrivateDslProperty`

Controls generated accessor behavior for private properties. Defaults differ from `@PublicDslProperty` — `wrapInFunction` defaults to `true`.

| Parameter        | Type      | Default | Description                                                  |
|------------------|-----------|---------|--------------------------------------------------------------|
| `restrictSetter` | `Boolean` | `false` | When `true`, disables the direct setter                      |
| `wrapInFunction` | `Boolean` | `true`  | When `true`, generates a function-based accessor             |

```kotlin
@GeneratedDsl
data class Person(
    @PrivateDslProperty
    private val age: Int,                              // setter + function

    @PrivateDslProperty(restrictSetter = true)
    private val position: Int = 0,                     // disables setter

    @PrivateDslProperty(wrapInFunction = false)
    private val amount: Int                            // restricts function call
)
```

#### `@DefaultValue`

Specifies a default value for a property in the generated builder.

| Parameter     | Type      | Default    | Description                                                            |
|---------------|-----------|------------|------------------------------------------------------------------------|
| `value`       | `String`  | *required* | Default value as a Kotlin expression (e.g., `"42"`, `"CAPTAIN"`)       |
| `packageName` | `String`  | `""`       | Package of the default value's type (ignored when `inferType=true`)    |
| `className`   | `String`  | `""`       | Class name of the default value's type (ignored when `inferType=true`) |
| `inferType`   | `Boolean` | `true`     | Auto-resolve type from property declaration                            |

#### `@DslAlias`

Generates additional accessor functions under alternative names for a property.

| Parameter | Type            | Default    | Description                            |
|-----------|-----------------|------------|----------------------------------------|
| `names`   | `vararg String` | *required* | One or more alternative accessor names |

```kotlin
@GeneratedDsl
data class Config(
    @DslAlias("desc")
    val description: String? = null,

    @DslAlias("cmds", "instructions")
    val commands: List<String>? = null
)
```

#### `@DeprecatedDsl`

Marks a generated accessor as deprecated, adding `@Deprecated` to generated functions.

| Parameter     | Type     | Default    | Description                              |
|---------------|----------|------------|------------------------------------------|
| `message`     | `String` | *required* | Deprecation message shown in IDE         |
| `replaceWith` | `String` | `""`       | Replacement expression for IDE quick-fix |

```kotlin
@GeneratedDsl
data class Config(
    val endpoint: String? = null,

    @DeprecatedDsl(message = "Use 'endpoint' instead.", replaceWith = "endpoint")
    val url: String? = null
)
```

#### `@ValidateDsl`

Adds a custom validation expression checked at build time in the generated `build()` method.

| Parameter    | Type     | Default    | Description                                                         |
|--------------|----------|------------|---------------------------------------------------------------------|
| `expression` | `String` | *required* | Kotlin boolean expression; use `it` to reference the property value |
| `message`    | `String` | `""`       | Custom error message for the `require` call                         |

```kotlin
@GeneratedDsl
data class Config(
    @ValidateDsl(expression = "it > 0", message = "capacity must be positive")
    val capacity: Int,

    @ValidateDsl(expression = "it.length <= 255", message = "name too long")
    val name: String
)
```

#### `@ListDsl`

Configures constraints, transformations, and accessor generation for `List` properties. Combines the functionality of the deprecated `@ListConfig` and `@DslProperty`.

| Parameter        | Type      | Default | Description                                         |
|------------------|-----------|---------|-----------------------------------------------------|
| `minSize`        | `Int`     | `-1`    | Minimum elements required (`-1` = no minimum)       |
| `maxSize`        | `Int`     | `-1`    | Maximum elements allowed (`-1` = no maximum)        |
| `uniqueElements` | `Boolean` | `false` | Remove duplicates via `.distinct()` before building |
| `sorted`         | `Boolean` | `false` | Sort elements via `.sorted()` before building       |
| `withVararg`     | `Boolean` | `true`  | Generate vararg accessor (e.g., `tags("a", "b")`)   |
| `withProvider`   | `Boolean` | `true`  | Generate provider accessor (e.g., `tags { listOf("a") }`) |

```kotlin
@GeneratedDsl
data class Config(
    @ListDsl(minSize = 1, maxSize = 10, uniqueElements = true)
    val tags: List<String>,

    @ListDsl(sorted = true, withVararg = false)
    val priorities: List<Int>
)
```

#### `@MapDsl`

Configures constraints and accessor generation for `Map` properties. Combines the functionality of the deprecated `@MapConfig` and `@DslProperty`.

| Parameter      | Type      | Default | Description                                  |
|----------------|-----------|---------|----------------------------------------------|
| `minSize`      | `Int`     | `-1`    | Minimum entries required (`-1` = no minimum) |
| `maxSize`      | `Int`     | `-1`    | Maximum entries allowed (`-1` = no maximum)  |
| `withVararg`   | `Boolean` | `true`  | Generate vararg accessor                     |
| `withProvider` | `Boolean` | `true`  | Generate provider accessor                   |

#### `@TransientDsl`

Excludes a property from the generated DSL builder entirely.

| Parameter | Type     | Default | Description                                         |
|-----------|----------|---------|-----------------------------------------------------|
| `reason`  | `String` | `""`    | Optional reason for excluding the property          |

#### `@InjectDslMethod`

Marks a property for method injection into the generated DSL builder. No parameters.

#### `@SingleEntryTransformDsl`

Enables single-input transform functions for properties with single-constructor types.

| Parameter           | Type        | Default    | Description                               |
|---------------------|-------------|------------|-------------------------------------------|
| `inputType`         | `KClass<T>` | *required* | The input type for the transform          |
| `transformTemplate` | `String`    | `""`       | Transform template (e.g., `Duration(%N)`) |

## Nested Configuration

```kotlin
@GeneratedDsl
data class BloomBuildPlannerQueue(
    val maxQueuedTasksPerTenant: Int,
    val storeTasksOnDisk: Boolean,
    val tasksDiskDirectory: String,
    val cleanTasksDirectory: Boolean
)

@GeneratedDsl
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

## Runtime Utilities

### `CoreDslBuilder<T>`

Interface implemented by all generated builders. Defines the `build(): T` contract.

### `DslValidation`

Singleton object with validation functions called by generated `build()` methods:

| Function                                         | Purpose                                              |
|--------------------------------------------------|------------------------------------------------------|
| `requireNotNull(accessor)`                       | Ensures a property is not null                       |
| `requireCollectionNotEmpty(value, name)`         | Validates collection is not null/empty               |
| `requireCollectionNotEmpty(accessor: KProperty)` | Validates collection property is not null/empty      |
| `requireCollectionNotEmpty(accessor: KFunction)` | Validates collection from function is not null/empty |
| `requireMapNotEmpty(map, name)`                  | Validates map is not null/empty                      |
| `requireMapNotEmpty(accessor: KProperty)`        | Validates map property is not null/empty             |
| `requireMapNotEmpty(accessor: KFunction)`        | Validates map from function is not null/empty        |
| `requireMinSize(collection, minSize, name)`      | Validates collection has at least N elements         |
| `requireMaxSize(collection, maxSize, name)`      | Validates collection has at most N elements          |
| `requireMinSize(map, minSize, name)`             | Validates map has at least N entries                 |
| `requireMaxSize(map, maxSize, name)`             | Validates map has at most N entries                  |

### `MapGroupType`

Enum for map grouping strategies: `NONE`, `SINGLE`, `LIST`, `ALL`. Includes `ACTIVE_TYPES` companion property for
non-NONE types.

## Deprecated Annotations

The following annotations have been replaced and will be removed in a future version:

| Deprecated         | Replacement                          |
|--------------------|--------------------------------------|
| `@Alias`           | `@DslAlias`                          |
| `@Description`     | `@DslDescription`                    |
| `@Validate`        | `@ValidateDsl`                       |
| `@ListConfig`      | `@ListDsl`                           |
| `@MapConfig`       | `@MapDsl`                            |
| `@DslProperty`     | `@ListDsl` / `@MapDsl` / `@PublicDslProperty` / `@PrivateDslProperty` |

## Configuration Options

| KSP Argument           | Description                          | Example                         |
|------------------------|--------------------------------------|---------------------------------|
| `projectRootClasspath` | Root package for your project        | `com.yourcompany.project`       |
| `dslBuilderClasspath`  | Package where builders are generated | `com.yourcompany.project.dsl`   |
| `dslMarkerClass`       | Custom DSL marker annotation class   | `com.yourcompany.project.MyDSL` |

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