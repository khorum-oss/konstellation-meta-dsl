package org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.standard

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.standard.DefaultTrue.NegationFunctionTemplate
import org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.standard.DefaultTrue.ValidFunctionTemplate
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
            assertEquals(
                listOf("negationFunctionName", "negationTemplate", "validFunctionName", "validTemplate"),
                paramNames
            )
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

        @Test
        fun `validFunctionName has a default`() {
            val param = DefaultTrue::class.primaryConstructor!!.parameters.first { it.name == "validFunctionName" }
            assertTrue(param.isOptional, "validFunctionName should have a default value")
        }

        @Test
        fun `validTemplate has a default`() {
            val param = DefaultTrue::class.primaryConstructor!!.parameters.first { it.name == "validTemplate" }
            assertTrue(param.isOptional, "validTemplate should have a default value")
        }
    }

    @Nested
    inner class NegationTemplateEnum {
        @Test
        fun `all enum values are present`() {
            val values = NegationFunctionTemplate.entries
            assertEquals(16, values.size)
        }

        @Test
        fun `contains expected entries`() {
            val values = NegationFunctionTemplate.entries
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

        @Test
        fun `all templates contain placeholder except NONE`() {
            NegationFunctionTemplate.entries
                .filter { it != NegationFunctionTemplate.NONE }
                .forEach { template ->
                    assertTrue(
                        template.template.contains("{x}"),
                        "${template.name} should contain {x} placeholder but was '${template.template}'"
                    )
                }
        }
    }

    @Nested
    inner class ValidTemplateEnum {
        @Test
        fun `all enum values are present`() {
            val values = ValidFunctionTemplate.entries
            assertEquals(10, values.size)
        }

        @Test
        fun `contains expected entries`() {
            val values = ValidFunctionTemplate.entries
            val expectedNames = listOf(
                "NONE", "IS", "DOES", "HAS", "ENABLED",
                "IS_ENABLED", "WITH", "PRESENT", "IS_PRESENT", "ALWAYS"
            )
            expectedNames.forEach { name ->
                assertNotNull(values.find { it.name == name }, "Missing ValidFunctionTemplate entry: $name")
            }
        }

        @Test
        fun `template values are correct`() {
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
                .filter { it != ValidFunctionTemplate.NONE }
                .forEach { template ->
                    assertTrue(
                        template.template.contains("{x}"),
                        "${template.name} should contain {x} placeholder but was '${template.template}'"
                    )
                }
        }
    }
}
