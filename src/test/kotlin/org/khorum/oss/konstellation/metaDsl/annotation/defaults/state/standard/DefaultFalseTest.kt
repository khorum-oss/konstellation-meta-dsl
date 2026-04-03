package org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.standard

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.standard.DefaultFalse.ValidFunctionTemplate
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DefaultFalseTest {

    @Nested
    inner class AnnotationParameters {
        @Test
        fun `has expected parameters`() {
            val constructor = DefaultFalse::class.primaryConstructor!!
            val paramNames = constructor.parameters
                .filter { it.kind == KParameter.Kind.VALUE }
                .map { it.name }
            assertEquals(listOf("validFunctionName", "validTemplate"), paramNames)
        }

        @Test
        fun `validFunctionName has a default`() {
            val param = DefaultFalse::class.primaryConstructor!!.parameters.first { it.name == "validFunctionName" }
            assertTrue(param.isOptional, "validFunctionName should have a default value")
        }

        @Test
        fun `validTemplate has a default`() {
            val param = DefaultFalse::class.primaryConstructor!!.parameters.first { it.name == "validTemplate" }
            assertTrue(param.isOptional, "validTemplate should have a default value")
        }
    }

    @Nested
    inner class ValidFunctionTemplateEnum {
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