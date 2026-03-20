package org.khorum.oss.konstellation.metaDsl

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class DslValidationSizeTest {

    @Nested
    inner class RequireMinSizeCollection {
        @Test
        fun `passes when collection size equals minSize`() {
            val list = listOf("a", "b")
            val result = DslValidation.requireMinSize(list, 2, "items")
            assertEquals(list, result)
        }

        @Test
        fun `passes when collection size exceeds minSize`() {
            val list = listOf("a", "b", "c")
            val result = DslValidation.requireMinSize(list, 2, "items")
            assertEquals(list, result)
        }

        @Test
        fun `throws when collection size is below minSize`() {
            val ex = assertThrows<IllegalArgumentException> {
                DslValidation.requireMinSize(listOf("a"), 3, "items")
            }
            assertEquals("items must have at least 3 elements, but has 1", ex.message)
        }

        @Test
        fun `passes with empty collection when minSize is 0`() {
            val result = DslValidation.requireMinSize(emptyList<String>(), 0, "items")
            assertEquals(emptyList<String>(), result)
        }
    }

    @Nested
    inner class RequireMaxSizeCollection {
        @Test
        fun `passes when collection size equals maxSize`() {
            val list = listOf("a", "b")
            val result = DslValidation.requireMaxSize(list, 2, "items")
            assertEquals(list, result)
        }

        @Test
        fun `passes when collection size is below maxSize`() {
            val list = listOf("a")
            val result = DslValidation.requireMaxSize(list, 5, "items")
            assertEquals(list, result)
        }

        @Test
        fun `throws when collection size exceeds maxSize`() {
            val ex = assertThrows<IllegalArgumentException> {
                DslValidation.requireMaxSize(listOf("a", "b", "c"), 2, "items")
            }
            assertEquals("items must have at most 2 elements, but has 3", ex.message)
        }

        @Test
        fun `throws when non-empty collection exceeds maxSize of 0`() {
            val ex = assertThrows<IllegalArgumentException> {
                DslValidation.requireMaxSize(listOf("a"), 0, "items")
            }
            assertEquals("items must have at most 0 elements, but has 1", ex.message)
        }

        @Test
        fun `passes with empty collection when maxSize is 0`() {
            val result = DslValidation.requireMaxSize(emptyList<String>(), 0, "items")
            assertEquals(emptyList<String>(), result)
        }
    }

    @Nested
    inner class RequireMinSizeMap {
        @Test
        fun `passes when map size equals minSize`() {
            val map = mapOf("a" to 1, "b" to 2)
            val result = DslValidation.requireMinSize(map, 2, "entries")
            assertEquals(map, result)
        }

        @Test
        fun `passes when map size exceeds minSize`() {
            val map = mapOf("a" to 1, "b" to 2, "c" to 3)
            val result = DslValidation.requireMinSize(map, 2, "entries")
            assertEquals(map, result)
        }

        @Test
        fun `throws when map size is below minSize`() {
            val ex = assertThrows<IllegalArgumentException> {
                DslValidation.requireMinSize(mapOf("a" to 1), 3, "entries")
            }
            assertEquals("entries must have at least 3 entries, but has 1", ex.message)
        }

        @Test
        fun `passes with empty map when minSize is 0`() {
            val result = DslValidation.requireMinSize(emptyMap<String, Int>(), 0, "entries")
            assertEquals(emptyMap<String, Int>(), result)
        }
    }

    @Nested
    inner class RequireMaxSizeMap {
        @Test
        fun `passes when map size equals maxSize`() {
            val map = mapOf("a" to 1, "b" to 2)
            val result = DslValidation.requireMaxSize(map, 2, "entries")
            assertEquals(map, result)
        }

        @Test
        fun `passes when map size is below maxSize`() {
            val map = mapOf("a" to 1)
            val result = DslValidation.requireMaxSize(map, 5, "entries")
            assertEquals(map, result)
        }

        @Test
        fun `throws when map size exceeds maxSize`() {
            val ex = assertThrows<IllegalArgumentException> {
                DslValidation.requireMaxSize(mapOf("a" to 1, "b" to 2, "c" to 3), 2, "entries")
            }
            assertEquals("entries must have at most 2 entries, but has 3", ex.message)
        }

        @Test
        fun `passes with empty map when maxSize is 0`() {
            val result = DslValidation.requireMaxSize(emptyMap<String, Int>(), 0, "entries")
            assertEquals(emptyMap<String, Int>(), result)
        }
    }
}
