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
 *     // normal interaction (function)
 *     @PrivateDslProperty
 *     private val age: Int,
 *
 *     // would enable setter
 *     @PrivateDslProperty(restrictSetter = false)
 *     private val position: Int = 0,
 *
 *     // would restrict the function call and setter
 *     @PrivateDslProperty(wrapInFunction = false)
 *     private val amount: Int,
 *
 *     // would restrict function but enable setter
 *     @PrivateDslProperty(wrapInFunction = false, restrictSetter = false)
 *     private val address: String,
 *
 *     // does nothing
 *     @PrivateDslProperty
 *     private val location; String? = null,
 *
 * )
 * ```
 *
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class PrivateDslProperty(
    val restrictSetter: Boolean = true,
    val wrapInFunction: Boolean = true
)
