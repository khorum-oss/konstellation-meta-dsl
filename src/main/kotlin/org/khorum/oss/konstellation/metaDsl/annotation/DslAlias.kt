package org.khorum.oss.konstellation.metaDsl.annotation

/**
 * Generates additional accessor functions under alternative names for a DSL property.
 *
 * Each alias name produces a duplicate accessor that delegates to the same underlying property.
 * Useful for providing shorthand names or domain-specific terminology.
 *
 * Example usage:
 * ```kotlin
 * @GeneratedDsl
 * data class Config(
 *     @Alias("desc")
 *     val description: String? = null,
 *
 *     @Alias("cmds", "instructions")
 *     val commands: List<String>? = null
 * )
 * ```
 *
 * Generated DSL usage:
 * ```kotlin
 * config {
 *     description = "full name works"
 *     desc("shorthand also works")
 *     commands("cmd1", "cmd2")
 *     cmds("cmd3")           // alias
 *     instructions("cmd4")   // alias
 * }
 * ```
 *
 * @property names One or more alternative accessor names. Each must be a valid Kotlin identifier.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class DslAlias(vararg val names: String)
