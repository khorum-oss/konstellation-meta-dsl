package org.khorum.oss.konstellation.metaDsl.annotation

/**
 * Marks a property as the root DSL object.
 * Will default to the class name if not specified.
 *
 * @param name The name of the root DSL object - overrides the class name.
 * @param alias An optional alias for the root DSL object - will create a separate builder..
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class RootDsl(val name: String = "", val alias: String = "")
