package org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.standard

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.standard.DefaultTrue.NegationTemplate
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DefaultTrueTest {

    @Nested
    inner class AnnotationParameters {
        @Test
        fun `has expected parameters`() {
            val constructor = DefaultTrue::class.primaryConstructor!!
            val paramNames = constructor.parameters
                .filter { it.kind == KParameter.Kind.VALUE }
                .map { it.name }
            assertEquals(listOf("negationFunctionName", "negationTemplate"), paramNames)
        }

        @Test
        fun `negationFunctionName has a default`() {
            val param = DefaultTrue::class.primaryConstructor!!.parameters.first { it.name == "negationFunctionName" }
            assertTrue(param.isOptional, "negationFunctionName should have a default value")
        }

        @Test
        fun `negationTemplate has a default`() {
            val param = DefaultTrue::class.primaryConstructor!!.parameters.first { it.name == "negationTemplate" }
            assertTrue(param.isOptional, "negationTemplate should have a default value")
        }
    }

    @Nested
    inner class NegationTemplateEnum {
        @Test
        fun `all enum values are present`() {
            val values = NegationTemplate.entries
            assertEquals(16, values.size)
        }

        @Test
        fun `contains expected entries`() {
            val values = NegationTemplate.entries
            val expectedNames = listOf(
                "NONE", "DOES_NOT", "DOES_NOT_HAVE", "IS_DISABLED", "DISABLED",
                "NOT", "IS_NOT", "HAS_NOT", "LACKS", "NO",
                "WITHOUT", "MISSING", "IS_MISSING", "ABSENT", "IS_ABSENT", "NEVER"
            )
            expectedNames.forEach { name ->
                assertNotNull(values.find { it.name == name }, "Missing NegationTemplate entry: $name")
            }
        }

        @Test
        fun `template values are correct`() {
            assertEquals("", NegationTemplate.NONE.template)
            assertEquals("doesNot{x}", NegationTemplate.DOES_NOT.template)
            assertEquals("doesNotHave{x}", NegationTemplate.DOES_NOT_HAVE.template)
            assertEquals("{x}IsDisabled", NegationTemplate.IS_DISABLED.template)
            assertEquals("{x}Disabled", NegationTemplate.DISABLED.template)
            assertEquals("not{x}", NegationTemplate.NOT.template)
            assertEquals("isNot{x}", NegationTemplate.IS_NOT.template)
            assertEquals("hasNot{x}", NegationTemplate.HAS_NOT.template)
            assertEquals("lacks{x}", NegationTemplate.LACKS.template)
            assertEquals("no{x}", NegationTemplate.NO.template)
            assertEquals("without{x}", NegationTemplate.WITHOUT.template)
            assertEquals("{x}Missing", NegationTemplate.MISSING.template)
            assertEquals("{x}IsMissing", NegationTemplate.IS_MISSING.template)
            assertEquals("{x}Absent", NegationTemplate.ABSENT.template)
            assertEquals("{x}IsAbsent", NegationTemplate.IS_ABSENT.template)
            assertEquals("never{x}", NegationTemplate.NEVER.template)
        }

        @Test
        fun `all templates contain placeholder except NONE`() {
            NegationTemplate.entries
                .filter { it != NegationTemplate.NONE }
                .forEach { template ->
                    assertTrue(
                        template.template.contains("{x}"),
                        "${template.name} should contain {x} placeholder but was '${template.template}'"
                    )
                }
        }
    }
}