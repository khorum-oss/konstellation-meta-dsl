package org.khorum.oss.konstellation.metaDsl.annotation.defaults

/**
 * Specifies a default enum constant for a property in the generated DSL builder.
 *
 * By default the KSP processor infers the enum class from the annotated property's
 * declared type and emits `EnumClass.VALUE` as the initializer.
 *
 * For edge cases where type inference fails (type aliases, complex generics),
 * provide explicit [packageName] and [className] so the processor can qualify
 * the constant.
 *
 * ```kotlin
 * @GeneratedDsl
 * data class Ship(
 *     @DefaultEnum("CAPTAIN")
 *     val role: Role   // generates: var role: Role = Role.CAPTAIN
 * )
 * ```
 *
 * **Mutual exclusivity:** A property must not have both `@DefaultEnum` and
 * `@DefaultValue` or `@DefaultState`. The KSP processor will emit an error
 * if more than one default annotation is present.
 *
 * @property value The enum constant name (e.g. `"CAPTAIN"`, `"ACTIVE"`).
 * @property packageName Explicit package of the enum class. Used only when type inference fails.
 * @property className Explicit enum class name. Used only when type inference fails.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class DefaultEnum(
    val value: String,
    val packageName: String = "",
    val className: String = ""
)
