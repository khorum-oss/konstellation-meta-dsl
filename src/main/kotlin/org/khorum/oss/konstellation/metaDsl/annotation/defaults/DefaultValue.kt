package org.khorum.oss.konstellation.metaDsl.annotation.defaults

/**
 * Annotation to specify a default value for a property in the generated DSL.
 *
 * When [inferType] is `true` (the default), the KSP processor resolves the property's type
 * automatically, so [packageName] and [className] can be omitted for standard Kotlin types
 * (`String`, `Int`, `Long`, `Boolean`, `Float`, `Double`, `Short`, `Byte`, `Char`) and enums.
 *
 * For custom types, either set [inferType] to `false` and provide explicit [packageName]/[className],
 * or rely on inference if the type is resolvable from the property declaration.
 *
 * **Breaking change:** Prior versions defaulted to `packageName = "kotlin"`, `className = "String"`.
 * These now default to empty strings when `inferType = true`.
 *
 * @property value The default value as a string (Kotlin code snippet, e.g. `"42"`, `"CAPTAIN"`, `"hello"`).
 * @property packageName Explicit package of the default value's type. Ignored when [inferType] is `true`.
 * @property className Explicit class name of the default value's type. Ignored when [inferType] is `true`.
 * @property inferType When `true`, the processor infers the type from the annotated property's declaration.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class DefaultValue(
    val value: String,
    val packageName: String = "",
    val className: String = "",
    val inferType: Boolean = true
)
