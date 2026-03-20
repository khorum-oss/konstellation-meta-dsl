package org.khorum.oss.konstellation.metaDsl.annotation

/**
 * Marks a DSL property's as not to be included in the generated DSL builder.
 *
 * @property reason The reason for excluding the property from the DSL builder.
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class TransientDsl(val reason: String = "")
