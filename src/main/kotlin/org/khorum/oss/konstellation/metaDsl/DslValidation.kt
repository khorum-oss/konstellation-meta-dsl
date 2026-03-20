package org.khorum.oss.konstellation.metaDsl

import kotlin.reflect.KFunction
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.isAccessible

/**
 * Centralized validation utilities for generated DSL builders.
 *
 * All validation functions used by Konstellation-generated `build()` methods are defined here.
 * Organized into sections: null checks, collection emptiness, map emptiness, and size constraints.
 */
@Suppress("TooManyFunctions")
object DslValidation {

    //region NULL CHECKS

    /**
     * Ensures the value returned by the given property reference is not `null`.
     *
     * @param accessor the property reference being validated
     * @return the non-null value of [accessor]
     * @throws IllegalArgumentException if the property value is null
     */
    fun <T> requireNotNull(accessor: KProperty<T?>): T {
        accessor.isAccessible = true
        return requireNotNull(accessor.getter.call()) { "${accessor.name} is required" }
    }

    //endregion NULL CHECKS

    //region COLLECTION EMPTINESS

    /**
     * Validates that the supplied collection is not null or empty.
     *
     * @param value the collection to validate
     * @param name the property name for error messages
     * @return the non-null, non-empty collection
     * @throws IllegalArgumentException if the collection is null or empty
     */
    fun <T, C : Collection<T>> requireCollectionNotEmpty(value: C?, name: String): C =
        requireCollectionNotEmptyInternal(value, name)

    /**
     * Validates that the collection held by the given property accessor is not null or empty.
     * Uses the accessor's name in the error message.
     *
     * @param accessor the property reference being validated
     * @return the non-null, non-empty collection
     * @throws IllegalArgumentException if the collection is null or empty
     */
    fun <T, C : Collection<T>> requireCollectionNotEmpty(accessor: KProperty<C?>): C {
        accessor.isAccessible = true
        return requireCollectionNotEmptyInternal(accessor.call(), accessor.name)
    }

    /**
     * Validates that the collection returned by the given function accessor is not null or empty.
     * Uses the accessor's name in the error message.
     *
     * @param accessor the function reference being validated
     * @return the non-null, non-empty collection
     * @throws IllegalArgumentException if the collection is null or empty
     */
    fun <T, C : Collection<T>> requireCollectionNotEmpty(accessor: KFunction<C?>): C {
        accessor.isAccessible = true
        return requireCollectionNotEmptyInternal(accessor.call(), accessor.name)
    }

    private fun <T, C : Collection<T>> requireCollectionNotEmptyInternal(value: C?, name: String): C {
        val returnedValue = if (value?.isEmpty() != false) null else value
        return kotlin.requireNotNull(returnedValue) { "$name is required and cannot be empty" }
    }

    //endregion COLLECTION EMPTINESS

    //region MAP EMPTINESS

    /**
     * Validates that the supplied map is not null or empty.
     *
     * @param map the map to validate
     * @param name the property name for error messages
     * @return the non-null, non-empty map
     * @throws IllegalArgumentException if the map is null or empty
     */
    fun <K, V, M : Map<K, V>> requireMapNotEmpty(map: M?, name: String): M =
        requireMapNotEmptyInternal(map, name)

    /**
     * Validates that the map held by the given property accessor is not null or empty.
     * Uses the accessor's name in the error message.
     *
     * @param accessor the property reference being validated
     * @return the non-null, non-empty map
     * @throws IllegalArgumentException if the map is null or empty
     */
    fun <K, V, M : Map<K, V>> requireMapNotEmpty(accessor: KProperty<M?>): M {
        accessor.isAccessible = true
        return requireMapNotEmptyInternal(accessor.call(), accessor.name)
    }

    /**
     * Validates that the map returned by the given function accessor is not null or empty.
     * Uses the accessor's name in the error message.
     *
     * @param accessor the function reference being validated
     * @return the non-null, non-empty map
     * @throws IllegalArgumentException if the map is null or empty
     */
    fun <K, V, M : Map<K, V>> requireMapNotEmpty(accessor: KFunction<M?>): M {
        accessor.isAccessible = true
        return requireMapNotEmptyInternal(accessor.call(), accessor.name)
    }

    private fun <K, V, M : Map<K, V>> requireMapNotEmptyInternal(map: M?, name: String): M {
        val returnedValue = if (map?.isEmpty() != false) null else map
        return kotlin.requireNotNull(returnedValue) { "$name is required and cannot be empty" }
    }

    //endregion MAP EMPTINESS

    //region SIZE CONSTRAINTS

    /**
     * Validates that the supplied collection has at least [minSize] elements.
     *
     * @param collection the collection to validate
     * @param minSize the minimum required size
     * @param name the property name for error messages
     * @return the collection if validation passes
     * @throws IllegalArgumentException if the collection has fewer than [minSize] elements
     */
    fun <T> requireMinSize(collection: Collection<T>, minSize: Int, name: String): Collection<T> {
        require(collection.size >= minSize) {
            "$name must have at least $minSize elements, but has ${collection.size}"
        }
        return collection
    }

    /**
     * Validates that the supplied collection has at most [maxSize] elements.
     *
     * @param collection the collection to validate
     * @param maxSize the maximum allowed size
     * @param name the property name for error messages
     * @return the collection if validation passes
     * @throws IllegalArgumentException if the collection has more than [maxSize] elements
     */
    fun <T> requireMaxSize(collection: Collection<T>, maxSize: Int, name: String): Collection<T> {
        require(collection.size <= maxSize) {
            "$name must have at most $maxSize elements, but has ${collection.size}"
        }
        return collection
    }

    /**
     * Validates that the supplied map has at least [minSize] entries.
     *
     * @param map the map to validate
     * @param minSize the minimum required size
     * @param name the property name for error messages
     * @return the map if validation passes
     * @throws IllegalArgumentException if the map has fewer than [minSize] entries
     */
    fun <K, V> requireMinSize(map: Map<K, V>, minSize: Int, name: String): Map<K, V> {
        require(map.size >= minSize) {
            "$name must have at least $minSize entries, but has ${map.size}"
        }
        return map
    }

    /**
     * Validates that the supplied map has at most [maxSize] entries.
     *
     * @param map the map to validate
     * @param maxSize the maximum allowed size
     * @param name the property name for error messages
     * @return the map if validation passes
     * @throws IllegalArgumentException if the map has more than [maxSize] entries
     */
    fun <K, V> requireMaxSize(map: Map<K, V>, maxSize: Int, name: String): Map<K, V> {
        require(map.size <= maxSize) {
            "$name must have at most $maxSize entries, but has ${map.size}"
        }
        return map
    }

    //endregion SIZE CONSTRAINTS
}
