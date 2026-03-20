@file:Suppress("TooManyFunctions")

package org.khorum.oss.konstellation.metaDsl

import kotlin.reflect.KFunction
import kotlin.reflect.KProperty

/**
 * Deprecated top-level validation functions. These delegate to [DslValidation] and exist
 * for backward compatibility with previously generated DSL code.
 *
 * New code should use [DslValidation] directly.
 */

@Deprecated("Use DslValidation.requireNotNull instead", ReplaceWith("DslValidation.requireNotNull(accessor)"))
fun <T> vRequireNotNull(accessor: KProperty<T?>): T =
    DslValidation.requireNotNull(accessor)

//region COLLECTION

@Deprecated(
    "Use DslValidation.requireCollectionNotEmpty instead",
    ReplaceWith("DslValidation.requireCollectionNotEmpty(value, name)")
)
fun <T, C : Collection<T>> vRequireCollectionNotEmpty(value: C?, name: String): C =
    DslValidation.requireCollectionNotEmpty(value, name)

@Deprecated(
    "Use DslValidation.requireCollectionNotEmpty instead",
    ReplaceWith("DslValidation.requireCollectionNotEmpty(accessor)")
)
fun <T, C : Collection<T>> vRequireCollectionNotEmpty(accessor: KProperty<C?>): C =
    DslValidation.requireCollectionNotEmpty(accessor)

@Deprecated(
    "Use DslValidation.requireCollectionNotEmpty instead",
    ReplaceWith("DslValidation.requireCollectionNotEmpty(accessor)")
)
fun <T, C : Collection<T>> vRequireCollectionNotEmpty(accessor: KFunction<C?>): C =
    DslValidation.requireCollectionNotEmpty(accessor)

//endregion COLLECTION

//region MAP

@Deprecated(
    "Use DslValidation.requireMapNotEmpty instead",
    ReplaceWith("DslValidation.requireMapNotEmpty(map, name)")
)
fun <K, V, M : Map<K, V>> vRequireMapNotEmpty(map: M?, name: String): M =
    DslValidation.requireMapNotEmpty(map, name)

@Deprecated(
    "Use DslValidation.requireMapNotEmpty instead",
    ReplaceWith("DslValidation.requireMapNotEmpty(accessor)")
)
fun <K, V, M : Map<K, V>> vRequireMapNotEmpty(accessor: KProperty<M?>): M =
    DslValidation.requireMapNotEmpty(accessor)

@Deprecated(
    "Use DslValidation.requireMapNotEmpty instead",
    ReplaceWith("DslValidation.requireMapNotEmpty(accessor)")
)
fun <K, V, M : Map<K, V>> vRequireMapNotEmpty(accessor: KFunction<M?>): M =
    DslValidation.requireMapNotEmpty(accessor)

//endregion MAP

//region SIZE CONSTRAINTS

@Deprecated(
    "Use DslValidation.requireMinSize instead",
    ReplaceWith("DslValidation.requireMinSize(collection, minSize, name)")
)
fun <T> vRequireMinSize(collection: Collection<T>, minSize: Int, name: String): Collection<T> =
    DslValidation.requireMinSize(collection, minSize, name)

@Deprecated(
    "Use DslValidation.requireMaxSize instead",
    ReplaceWith("DslValidation.requireMaxSize(collection, maxSize, name)")
)
fun <T> vRequireMaxSize(collection: Collection<T>, maxSize: Int, name: String): Collection<T> =
    DslValidation.requireMaxSize(collection, maxSize, name)

@Deprecated(
    "Use DslValidation.requireMinSize instead",
    ReplaceWith("DslValidation.requireMinSize(map, minSize, name)")
)
fun <K, V> vRequireMinSize(map: Map<K, V>, minSize: Int, name: String): Map<K, V> =
    DslValidation.requireMinSize(map, minSize, name)

@Deprecated(
    "Use DslValidation.requireMaxSize instead",
    ReplaceWith("DslValidation.requireMaxSize(map, maxSize, name)")
)
fun <K, V> vRequireMaxSize(map: Map<K, V>, maxSize: Int, name: String): Map<K, V> =
    DslValidation.requireMaxSize(map, maxSize, name)

//endregion SIZE CONSTRAINTS
