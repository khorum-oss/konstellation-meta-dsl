package org.khorum.oss.konstellation.metaDsl.annotation

/**
 * Marks a method for injection into the generated DSL builder.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class InjectDslMethod
