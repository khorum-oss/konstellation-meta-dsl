@file:Suppress("DEPRECATION")

package org.khorum.oss.konstellation.metaDsl

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

/**
 * Tests for the deprecated top-level `vRequire*` wrapper functions.
 * Verifies backward compatibility — these functions must continue to
 * delegate correctly to [DslValidation].
 */
class RequiredValidationTest {

    @Nested
    inner class VRequireNotNull {
        private var nullableField: String? = null
        private var presentField: String? = "hello"

        @Test
        fun `returns value when property is not null`() {
            val result = vRequireNotNull(::presentField)
            assertEquals("hello", result)
        }

        @Test
        fun `throws when property is null`() {
            val ex = assertThrows<IllegalArgumentException> {
                vRequireNotNull(::nullableField)
            }
            assertEquals("nullableField is required", ex.message)
        }
    }

    @Nested
    inner class VRequireCollectionNotEmpty {
        private var presentCollection: List<String>? = listOf("a", "b")
        private var nullCollection: List<String>? = null

        private fun presentCollectionFn(): List<String>? = listOf("x")
        private fun nullCollectionFn(): List<String>? = null

        @Test
        fun `by value - returns collection when not empty`() {
            val list = listOf("a", "b")
            val result = vRequireCollectionNotEmpty(list, "items")
            assertEquals(list, result)
        }

        @Test
        fun `by value - throws when null`() {
            val ex = assertThrows<IllegalArgumentException> {
                vRequireCollectionNotEmpty<String, List<String>>(null, "items")
            }
            assertEquals("items is required and cannot be empty", ex.message)
        }

        @Test
        fun `by property - returns collection when not empty`() {
            val result = vRequireCollectionNotEmpty(::presentCollection)
            assertEquals(listOf("a", "b"), result)
        }

        @Test
        fun `by property - throws when null`() {
            assertThrows<IllegalArgumentException> {
                vRequireCollectionNotEmpty(::nullCollection)
            }
        }

        @Test
        fun `by function - returns collection when not empty`() {
            val result = vRequireCollectionNotEmpty(::presentCollectionFn)
            assertEquals(listOf("x"), result)
        }

        @Test
        fun `by function - throws when null`() {
            assertThrows<IllegalArgumentException> {
                vRequireCollectionNotEmpty(::nullCollectionFn)
            }
        }
    }

    @Nested
    inner class VRequireMapNotEmpty {
        private var presentMap: Map<String, String>? = mapOf("k" to "v")
        private var nullMap: Map<String, String>? = null

        private fun presentMapFn(): Map<String, String>? = mapOf("k" to "v")
        private fun nullMapFn(): Map<String, String>? = null

        @Test
        fun `by value - returns map when not empty`() {
            val map = mapOf("k" to "v")
            val result = vRequireMapNotEmpty(map, "data")
            assertEquals(map, result)
        }

        @Test
        fun `by value - throws when null`() {
            val ex = assertThrows<IllegalArgumentException> {
                vRequireMapNotEmpty<String, String, Map<String, String>>(null, "data")
            }
            assertEquals("data is required and cannot be empty", ex.message)
        }

        @Test
        fun `by property - returns map when not empty`() {
            val result = vRequireMapNotEmpty(::presentMap)
            assertEquals(mapOf("k" to "v"), result)
        }

        @Test
        fun `by property - throws when null`() {
            assertThrows<IllegalArgumentException> {
                vRequireMapNotEmpty(::nullMap)
            }
        }

        @Test
        fun `by function - returns map when not empty`() {
            val result = vRequireMapNotEmpty(::presentMapFn)
            assertEquals(mapOf("k" to "v"), result)
        }

        @Test
        fun `by function - throws when null`() {
            assertThrows<IllegalArgumentException> {
                vRequireMapNotEmpty(::nullMapFn)
            }
        }
    }

    @Nested
    inner class VRequireMinSize {
        @Test
        fun `collection - passes at exact min`() {
            val result = vRequireMinSize(listOf("a", "b"), 2, "items")
            assertEquals(listOf("a", "b"), result)
        }

        @Test
        fun `collection - throws below min`() {
            val ex = assertThrows<IllegalArgumentException> {
                vRequireMinSize(listOf("a"), 3, "items")
            }
            assertEquals("items must have at least 3 elements, but has 1", ex.message)
        }

        @Test
        fun `map - passes at exact min`() {
            val result = vRequireMinSize(mapOf("a" to 1), 1, "entries")
            assertEquals(mapOf("a" to 1), result)
        }

        @Test
        fun `map - throws below min`() {
            val ex = assertThrows<IllegalArgumentException> {
                vRequireMinSize(emptyMap<String, Int>(), 1, "entries")
            }
            assertEquals("entries must have at least 1 entries, but has 0", ex.message)
        }
    }

    @Nested
    inner class VRequireMaxSize {
        @Test
        fun `collection - passes at exact max`() {
            val result = vRequireMaxSize(listOf("a", "b"), 2, "items")
            assertEquals(listOf("a", "b"), result)
        }

        @Test
        fun `collection - throws above max`() {
            val ex = assertThrows<IllegalArgumentException> {
                vRequireMaxSize(listOf("a", "b", "c"), 2, "items")
            }
            assertEquals("items must have at most 2 elements, but has 3", ex.message)
        }

        @Test
        fun `map - passes at exact max`() {
            val result = vRequireMaxSize(mapOf("a" to 1), 1, "entries")
            assertEquals(mapOf("a" to 1), result)
        }

        @Test
        fun `map - throws above max`() {
            val ex = assertThrows<IllegalArgumentException> {
                vRequireMaxSize(mapOf("a" to 1, "b" to 2), 1, "entries")
            }
            assertEquals("entries must have at most 1 entries, but has 2", ex.message)
        }
    }
}
