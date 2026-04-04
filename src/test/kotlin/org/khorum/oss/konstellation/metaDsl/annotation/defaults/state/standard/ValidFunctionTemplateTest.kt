package org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.standard

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ValidFunctionTemplateTest {
    @Test
    fun `all enum values are present`() {
        val values = ValidFunctionTemplate.entries
        assertEquals(11, values.size)
    }

    @Test
    fun `contains expected entries`() {
        val values = ValidFunctionTemplate.entries
        val expectedNames = listOf(
            "SELF", "NONE", "IS", "DOES", "HAS", "ENABLED",
            "IS_ENABLED", "WITH", "PRESENT", "IS_PRESENT", "ALWAYS"
        )
        expectedNames.forEach { name ->
            assertNotNull(values.find { it.name == name }, "Missing ValidFunctionTemplate entry: $name")
        }
    }

    @Test
    fun `template values are correct`() {
        assertEquals("", ValidFunctionTemplate.SELF.template)
        assertEquals("", ValidFunctionTemplate.NONE.template)
        assertEquals("is{x}", ValidFunctionTemplate.IS.template)
        assertEquals("does{x}", ValidFunctionTemplate.DOES.template)
        assertEquals("has{x}", ValidFunctionTemplate.HAS.template)
        assertEquals("{x}Enabled", ValidFunctionTemplate.ENABLED.template)
        assertEquals("{x}IsEnabled", ValidFunctionTemplate.IS_ENABLED.template)
        assertEquals("with{x}", ValidFunctionTemplate.WITH.template)
        assertEquals("{x}Present", ValidFunctionTemplate.PRESENT.template)
        assertEquals("{x}IsPresent", ValidFunctionTemplate.IS_PRESENT.template)
        assertEquals("always{x}", ValidFunctionTemplate.ALWAYS.template)
    }

    @Test
    fun `all templates contain placeholder except NONE`() {
        ValidFunctionTemplate.entries
            .filter { it != ValidFunctionTemplate.NONE && it != ValidFunctionTemplate.SELF }
            .forEach { template ->
                assertTrue(
                    template.template.contains("{x}"),
                    "${template.name} should contain {x} placeholder but was '${template.template}'"
                )
            }
    }
}