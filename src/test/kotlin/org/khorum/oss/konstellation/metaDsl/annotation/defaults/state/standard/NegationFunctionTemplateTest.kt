package org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.standard

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class NegationFunctionTemplateTest {
    @Test
    fun `all enum values are present`() {
        val values = NegationFunctionTemplate.entries
        assertEquals(17, values.size)
    }

    @Test
    fun `contains expected entries`() {
        val values = NegationFunctionTemplate.entries
        val expectedNames = listOf(
            "SELF", "NONE", "DOES_NOT", "DOES_NOT_HAVE", "IS_DISABLED", "DISABLED",
            "NOT", "IS_NOT", "HAS_NOT", "LACKS", "NO",
            "WITHOUT", "MISSING", "IS_MISSING", "ABSENT", "IS_ABSENT", "NEVER"
        )
        expectedNames.forEach { name ->
            assertNotNull(values.find { it.name == name }, "Missing NegationTemplate entry: $name")
        }
    }

    @Test
    fun `all templates contain placeholder except NONE and SELF`() {
        NegationFunctionTemplate.entries
            .filter { it != NegationFunctionTemplate.NONE && it != NegationFunctionTemplate.SELF }
            .forEach { template ->
                assertTrue(
                    template.template.contains("{x}"),
                    "${template.name} should contain {x} placeholder but was '${template.template}'"
                )
            }
    }

    @Test
    fun `template values are correct`() {
        assertEquals("", NegationFunctionTemplate.SELF.template)
        assertEquals("", NegationFunctionTemplate.NONE.template)
        assertEquals("doesNot{x}", NegationFunctionTemplate.DOES_NOT.template)
        assertEquals("doesNotHave{x}", NegationFunctionTemplate.DOES_NOT_HAVE.template)
        assertEquals("{x}IsDisabled", NegationFunctionTemplate.IS_DISABLED.template)
        assertEquals("{x}Disabled", NegationFunctionTemplate.DISABLED.template)
        assertEquals("not{x}", NegationFunctionTemplate.NOT.template)
        assertEquals("isNot{x}", NegationFunctionTemplate.IS_NOT.template)
        assertEquals("hasNot{x}", NegationFunctionTemplate.HAS_NOT.template)
        assertEquals("lacks{x}", NegationFunctionTemplate.LACKS.template)
        assertEquals("no{x}", NegationFunctionTemplate.NO.template)
        assertEquals("without{x}", NegationFunctionTemplate.WITHOUT.template)
        assertEquals("{x}Missing", NegationFunctionTemplate.MISSING.template)
        assertEquals("{x}IsMissing", NegationFunctionTemplate.IS_MISSING.template)
        assertEquals("{x}Absent", NegationFunctionTemplate.ABSENT.template)
        assertEquals("{x}IsAbsent", NegationFunctionTemplate.IS_ABSENT.template)
        assertEquals("never{x}", NegationFunctionTemplate.NEVER.template)
    }
}