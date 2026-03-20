package org.khorum.oss.konstellation.metaDsl.annotation

/**
 * Annotation to configure DSL property generation for list and map properties.
 *
 * By default, list and map properties will generate both vararg and provider functions:
 * - Vararg: `names(vararg name: String)` - allows passing multiple items directly
 * - Provider: `names(provider: () -> List<String>)` - allows passing a lambda that returns the collection
 *
 * Use this annotation to disable either or both of these generated functions.
 *
 * Example usage:
 * ```kotlin
 * @GeneratedDsl
 * data class Person(
 *     // Generates just the setter
 *     val name: String,
 *
 *     // normal interaction
 *     @PublicDslProperty
 *     val age: Int,
 *
 *     // would disable setter totally
 *     @PublicDslProperty(restrictSetter = true)
 *     val position: Int = 0,
 *
 *     // would add an additional function as well as the setter
 *     @PublicDslProperty(wrapInFunction = true)
 *     val amount: Int,
 *
 *     // would restrict the setter to only use the function
 *     @PublicDslProperty(wrapInFunction = true, restrictSetter = true)
 *     val address: String,
 *
 *     // does nothing
 *     @PublicDslProperty
 *     private val location; String? = null,
 *
 * )
 * ```
 *
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class PublicDslProperty(
    val restrictSetter: Boolean = false,
    val wrapInFunction: Boolean = false
)
