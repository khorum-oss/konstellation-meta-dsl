package org.khorum.oss.konstellation.metaDsl.annotation

/**
 * Adds KDoc documentation to generated DSL builder classes and accessor functions.
 *
 * When applied to a class, the description becomes the KDoc on the generated builder class.
 * When applied to a property, the description becomes the KDoc on the generated property
 * and its accessor functions.
 *
 * Example usage:
 * ```kotlin
 * @GeneratedDsl
 * @Description("Builder for configuring a starship's properties.")
 * data class StarShip(
 *     @Description("The official designation of the starship.")
 *     val name: String,
 *
 *     @Description("List of commanding officers, in order of rank.")
 *     val commanderNames: List<String>
 * )
 * ```
 *
 * @property value The KDoc text to attach to the generated code.
 */
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class DslDescription(val value: String)
