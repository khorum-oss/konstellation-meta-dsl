package org.khorum.oss.konstellation.metaDsl.annotation

/**
 * Adds a custom validation expression to a DSL property, checked at build time.
 *
 * The KSP processor emits a `require(expression) { message }` call in the generated
 * `build()` method. The expression is raw Kotlin code where `it` refers to the property value.
 *
 * **Note:** The expression is emitted as-is into generated code and is verified by the Kotlin
 * compiler, not sandboxed at runtime.
 *
 * Example usage:
 * ```kotlin
 * @GeneratedDsl
 * data class Config(
 *     @Validate(expression = "it > 0", message = "capacity must be positive")
 *     val capacity: Int,
 *
 *     @Validate(expression = "it.length <= 255", message = "name too long")
 *     val name: String
 * )
 * ```
 *
 * @property expression A Kotlin expression that evaluates to `Boolean`. Use `it` to reference the property value.
 * @property message Custom error message for the `require` call. If empty, a default message is generated.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class ValidateDsl(
    val expression: String,
    val message: String = ""
)
