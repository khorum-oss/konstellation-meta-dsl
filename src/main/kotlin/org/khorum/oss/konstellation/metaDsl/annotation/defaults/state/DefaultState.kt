package org.khorum.oss.konstellation.metaDsl.annotation.defaults.state

/**
 * Specifies a predefined default state for a property in the generated DSL builder.
 *
 * Use this annotation for common defaults (empty string, zero, empty collections, booleans)
 * instead of [@DefaultValue][org.khorum.oss.konstellation.metaDsl.annotation.defaults.DefaultValue] to avoid string-escaping issues and improve readability.
 *
 * ```kotlin
 * @GeneratedDsl
 * data class Config(
 *     @DefaultState(DefaultStateType.EMPTY_STRING)
 *     val name: String,
 *
 *     @DefaultState(DefaultStateType.ZERO_INT)
 *     val retryCount: Int,
 *
 *     @DefaultState(DefaultStateType.EMPTY_LIST)
 *     val tags: MutableList<String>,
 *
 *     @DefaultState(DefaultStateType.FALSE)
 *     val enabled: Boolean
 * )
 * ```
 *
 * **Mutual exclusivity:** A property must not have both `@DefaultState` and `@DefaultValue`.
 * The KSP processor will emit an error if both are present.
 *
 * @property type The predefined default state to apply. See [DefaultStateType] for available options.
 */
@Target(allowedTargets = [
    AnnotationTarget.PROPERTY,
    AnnotationTarget.ANNOTATION_CLASS
])
@Retention(AnnotationRetention.SOURCE)
annotation class DefaultState(val type: DefaultStateType)
