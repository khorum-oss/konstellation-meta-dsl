package org.khorum.oss.konstellation.metaDsl.annotation

/**
 * Marks a DSL property's generated accessor as deprecated.
 *
 * The KSP processor adds a `@Deprecated` annotation to the generated accessor functions,
 * which triggers IDE warnings and suggestions for replacement. Useful when evolving a DSL
 * while maintaining backward compatibility.
 *
 * Example usage:
 * ```kotlin
 * @GeneratedDsl
 * data class Config(
 *     val endpoint: String? = null,
 *
 *     @DeprecatedDsl(
 *         message = "Use 'endpoint' instead.",
 *         replaceWith = "endpoint"
 *     )
 *     val url: String? = null
 * )
 * ```
 *
 * @property message The deprecation message shown in IDE warnings.
 * @property replaceWith Optional replacement expression for `ReplaceWith`. Empty string means no replacement.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class DeprecatedDsl(
    val message: String,
    val replaceWith: String = ""
)
