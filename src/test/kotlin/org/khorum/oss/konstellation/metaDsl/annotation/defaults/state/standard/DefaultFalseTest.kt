package org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.standard

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor
import kotlin.test.assertEquals
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
            assertEquals(
                listOf("validFunctionName", "validTemplate", "negationFunctionName", "negationTemplate"),
                paramNames
            )
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

        @Test
        fun `negationFunctionName has a default`() {
            val param = DefaultFalse::class.primaryConstructor!!.parameters.first { it.name == "negationFunctionName" }
            assertTrue(param.isOptional, "negationFunctionName should have a default value")
        }

        @Test
        fun `negationTemplate has a default`() {
            val param = DefaultFalse::class.primaryConstructor!!.parameters.first { it.name == "negationTemplate" }
            assertTrue(param.isOptional, "negationTemplate should have a default value")
        }
    }
}
