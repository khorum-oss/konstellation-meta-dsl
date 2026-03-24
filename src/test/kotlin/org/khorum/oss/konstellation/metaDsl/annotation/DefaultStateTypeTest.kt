package org.khorum.oss.konstellation.metaDsl.annotation

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DefaultStateTypeTest {

    @Test
    fun `all enum values are present`() {
        val values = DefaultStateType.entries
        assertEquals(9, values.size)
        assertTrue(values.contains(DefaultStateType.EMPTY_STRING))
        assertTrue(values.contains(DefaultStateType.ZERO_INT))
        assertTrue(values.contains(DefaultStateType.ZERO_LONG))
        assertTrue(values.contains(DefaultStateType.ZERO_DOUBLE))
        assertTrue(values.contains(DefaultStateType.ZERO_FLOAT))
        assertTrue(values.contains(DefaultStateType.EMPTY_LIST))
        assertTrue(values.contains(DefaultStateType.EMPTY_MAP))
        assertTrue(values.contains(DefaultStateType.TRUE))
        assertTrue(values.contains(DefaultStateType.FALSE))
    }

    @Test
    fun `codeSnippet values are correct`() {
        assertEquals("\"\"", DefaultStateType.EMPTY_STRING.codeSnippet)
        assertEquals("0", DefaultStateType.ZERO_INT.codeSnippet)
        assertEquals("0L", DefaultStateType.ZERO_LONG.codeSnippet)
        assertEquals("0.0", DefaultStateType.ZERO_DOUBLE.codeSnippet)
        assertEquals("0.0f", DefaultStateType.ZERO_FLOAT.codeSnippet)
        assertEquals("mutableListOf()", DefaultStateType.EMPTY_LIST.codeSnippet)
        assertEquals("mutableMapOf()", DefaultStateType.EMPTY_MAP.codeSnippet)
        assertEquals("true", DefaultStateType.TRUE.codeSnippet)
        assertEquals("false", DefaultStateType.FALSE.codeSnippet)
    }

    @Test
    fun `NUMERIC_TYPES contains expected values`() {
        assertEquals(
            listOf(DefaultStateType.ZERO_INT, DefaultStateType.ZERO_LONG, DefaultStateType.ZERO_DOUBLE, DefaultStateType.ZERO_FLOAT),
            DefaultStateType.NUMERIC_TYPES
        )
    }

    @Test
    fun `COLLECTION_TYPES contains expected values`() {
        assertEquals(
            listOf(DefaultStateType.EMPTY_LIST, DefaultStateType.EMPTY_MAP),
            DefaultStateType.COLLECTION_TYPES
        )
    }

    @Test
    fun `BOOLEAN_TYPES contains expected values`() {
        assertEquals(
            listOf(DefaultStateType.TRUE, DefaultStateType.FALSE),
            DefaultStateType.BOOLEAN_TYPES
        )
    }
}
