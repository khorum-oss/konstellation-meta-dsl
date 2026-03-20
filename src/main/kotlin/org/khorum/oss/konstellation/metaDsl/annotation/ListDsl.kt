package org.khorum.oss.konstellation.metaDsl.annotation

/**
 * Configures constraints and transformations for List properties in the generated DSL.
 *
 * Applied to `List` properties alongside or instead of [PublicDslProperty] to control
 * build-time validation and post-processing of the collected elements.
 *
 * Example usage:
 * ```kotlin
 * @GeneratedDsl
 * data class Config(
 *     @ListConfig(minSize = 1, maxSize = 10, uniqueElements = true)
 *     val tags: List<String>,
 *
 *     @ListConfig(sorted = true)
 *     val priorities: List<Int>
 *
 *     // Generates both vararg and provider (default)
 *     val tags: List<String>,
 *
 *     // Only generates provider function
 *     @DslProperty(withVararg = false)
 *     val aliases: List<String>,
 *
 *     // Only generates vararg function
 *     @DslProperty(withProvider = false)
 *     val nicknames: List<String>,
 *
 *     // Generates neither (property must be set directly)
 *     @DslProperty(withVararg = false, withProvider = false)
 *     val ids: List<Int>
 * }
 *
 * @property minSize Minimum number of elements required. -1 means no minimum. Validated at build time.
 * @property maxSize Maximum number of elements allowed. -1 means no maximum. Validated at build time.
 * @property uniqueElements When `true`, duplicate elements are removed (via `.distinct()`) before building.
 * @property sorted When `true`, elements are sorted (via `.sorted()`) before building.
 * @property withVararg Whether to generate a vararg function. Default is true.
 * @property withProvider Whether to generate a provider function. Default is true.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class ListDsl(
    val minSize: Int = -1,
    val maxSize: Int = -1,
    val uniqueElements: Boolean = false,
    val sorted: Boolean = false,
    val withVararg: Boolean = true,
    val withProvider: Boolean = true
)
