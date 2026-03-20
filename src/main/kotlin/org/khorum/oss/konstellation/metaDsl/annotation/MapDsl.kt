package org.khorum.oss.konstellation.metaDsl.annotation

/**
 * Configures constraints for Map properties in the generated DSL.
 *
 * Applied to `Map` properties alongside or instead of [PublicDslProperty] to control
 * build-time validation of the collected entries.
 *
 * Example usage:
 * ```kotlin
 * @GeneratedDsl
 * data class Config(
 *     @MapConfig(minSize = 1, maxSize = 50)
 *     val headers: Map<String, String>
 *
 *     // Generates both vararg and provider (default)
 *     val tags: Map<String, String>,
 *
 *     // Only generates provider function
 *     @DslProperty(withVararg = false)
 *     val aliases: Map<String, String>,
 *
 *     // Only generates vararg function
 *     @DslProperty(withProvider = false)
 *     val nicknames: Map<String, String>,
 *
 *     // Generates neither (property must be set directly)
 *     @DslProperty(withVararg = false, withProvider = false)
 *     val ids: Map<String, Int>
 * )
 * ```
 *
 * @property minSize Minimum number of entries required. -1 means no minimum. Validated at build time.
 * @property maxSize Maximum number of entries allowed. -1 means no maximum. Validated at build time.
 * @property withVararg Whether to generate a vararg function. Default is true.
 * @property withProvider Whether to generate a provider function. Default is true.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class MapDsl(
    val minSize: Int = -1,
    val maxSize: Int = -1,
    val withVararg: Boolean = true,
    val withProvider: Boolean = true
)
