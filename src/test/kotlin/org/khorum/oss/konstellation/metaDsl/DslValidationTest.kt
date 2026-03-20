package org.khorum.oss.konstellation.metaDsl

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class DslValidationTest {

    @Nested
    inner class RequireNotNull {
        private var nullableField: String? = null
        private var presentField: String? = "hello"

        @Test
        fun `returns value when property is not null`() {
            val result = DslValidation.requireNotNull(::presentField)
            assertEquals("hello", result)
        }

        @Test
        fun `throws when property is null`() {
            val ex = assertThrows<IllegalArgumentException> {
                DslValidation.requireNotNull(::nullableField)
            }
            assertEquals("nullableField is required", ex.message)
        }
    }

    @Nested
    inner class RequireCollectionNotEmptyByValue {
        @Test
        fun `returns collection when not empty`() {
            val list = listOf("a", "b")
            val result = DslValidation.requireCollectionNotEmpty(list, "items")
            assertEquals(list, result)
        }

        @Test
        fun `throws when collection is null`() {
            val ex = assertThrows<IllegalArgumentException> {
                DslValidation.requireCollectionNotEmpty<String, List<String>>(null, "items")
            }
            assertEquals("items is required and cannot be empty", ex.message)
        }

        @Test
        fun `throws when collection is empty`() {
            val ex = assertThrows<IllegalArgumentException> {
                DslValidation.requireCollectionNotEmpty(emptyList<String>(), "items")
            }
            assertEquals("items is required and cannot be empty", ex.message)
        }
    }

    @Nested
    inner class RequireCollectionNotEmptyByProperty {
        private var items: List<String>? = null
        private var filledItems: List<String>? = listOf("x")

        @Test
        fun `returns collection when property has non-empty value`() {
            val result = DslValidation.requireCollectionNotEmpty(::filledItems)
            assertEquals(listOf("x"), result)
        }

        @Test
        fun `throws when property is null`() {
            val ex = assertThrows<IllegalArgumentException> {
                DslValidation.requireCollectionNotEmpty(::items)
            }
            assertEquals("items is required and cannot be empty", ex.message)
        }
    }

    @Nested
    inner class RequireCollectionNotEmptyByFunction {
        private fun nullProvider(): List<String>? = null
        private fun emptyProvider(): List<String>? = emptyList()
        private fun filledProvider(): List<String>? = listOf("a")

        @Test
        fun `returns collection when function returns non-empty value`() {
            val result = DslValidation.requireCollectionNotEmpty(::filledProvider)
            assertEquals(listOf("a"), result)
        }

        @Test
        fun `throws when function returns null`() {
            val ex = assertThrows<IllegalArgumentException> {
                DslValidation.requireCollectionNotEmpty(::nullProvider)
            }
            assertEquals("nullProvider is required and cannot be empty", ex.message)
        }

        @Test
        fun `throws when function returns empty`() {
            val ex = assertThrows<IllegalArgumentException> {
                DslValidation.requireCollectionNotEmpty(::emptyProvider)
            }
            assertEquals("emptyProvider is required and cannot be empty", ex.message)
        }
    }

    @Nested
    inner class RequireMapNotEmptyByValue {
        @Test
        fun `returns map when not empty`() {
            val map = mapOf("k" to "v")
            val result = DslValidation.requireMapNotEmpty(map, "data")
            assertEquals(map, result)
        }

        @Test
        fun `throws when map is null`() {
            val ex = assertThrows<IllegalArgumentException> {
                DslValidation.requireMapNotEmpty<String, String, Map<String, String>>(null, "data")
            }
            assertEquals("data is required and cannot be empty", ex.message)
        }

        @Test
        fun `throws when map is empty`() {
            val ex = assertThrows<IllegalArgumentException> {
                DslValidation.requireMapNotEmpty(emptyMap<String, String>(), "data")
            }
            assertEquals("data is required and cannot be empty", ex.message)
        }
    }

    @Nested
    inner class RequireMapNotEmptyByProperty {
        private var data: Map<String, Int>? = null
        private var filledData: Map<String, Int>? = mapOf("a" to 1)

        @Test
        fun `returns map when property has non-empty value`() {
            val result = DslValidation.requireMapNotEmpty(::filledData)
            assertEquals(mapOf("a" to 1), result)
        }

        @Test
        fun `throws when property is null`() {
            val ex = assertThrows<IllegalArgumentException> {
                DslValidation.requireMapNotEmpty(::data)
            }
            assertEquals("data is required and cannot be empty", ex.message)
        }
    }

    @Nested
    inner class RequireMapNotEmptyByFunction {
        private fun nullProvider(): Map<String, Int>? = null
        private fun emptyProvider(): Map<String, Int>? = emptyMap()
        private fun filledProvider(): Map<String, Int>? = mapOf("a" to 1)

        @Test
        fun `returns map when function returns non-empty value`() {
            val result = DslValidation.requireMapNotEmpty(::filledProvider)
            assertEquals(mapOf("a" to 1), result)
        }

        @Test
        fun `throws when function returns null`() {
            val ex = assertThrows<IllegalArgumentException> {
                DslValidation.requireMapNotEmpty(::nullProvider)
            }
            assertEquals("nullProvider is required and cannot be empty", ex.message)
        }

        @Test
        fun `throws when function returns empty`() {
            val ex = assertThrows<IllegalArgumentException> {
                DslValidation.requireMapNotEmpty(::emptyProvider)
            }
            assertEquals("emptyProvider is required and cannot be empty", ex.message)
        }
    }
}
