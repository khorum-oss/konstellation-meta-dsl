package org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.standard

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor
import kotlin.test.assertEquals
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
}
