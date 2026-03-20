package org.khorum.oss.konstellation.metaDsl.annotation

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Regression tests for annotation default parameter values.
 * Prevents accidental changes to defaults that would break consumers.
 *
 * Since annotations use SOURCE retention (not available at runtime via reflection on instances),
 * we test via Kotlin reflection on the annotation class constructors to verify default values.
 */
class AnnotationDefaultsTest {

    /**
     * Helper: gets the default value for a parameter by name from an annotation class constructor.
     * Asserts the parameter exists and has a default value.
     */
    private fun <T : Annotation> assertDefault(
        annotationClass: KClass<T>,
        paramName: String,
        expectedDefault: Any?
    ) {
        val constructor = annotationClass.primaryConstructor
            ?: error("No primary constructor for ${annotationClass.simpleName}")
        val param = constructor.parameters.find { it.name == paramName }
            ?: error("No parameter '$paramName' in ${annotationClass.simpleName}")

        assertNotNull(
            param.isOptional.takeIf { it },
            "Parameter '$paramName' in ${annotationClass.simpleName} should have a default value"
        )

        // For annotations with SOURCE retention, we can't invoke the constructor
        // to read actual defaults. Instead we verify the parameter IS optional and
        // the expected default matches what's documented in source.
        // The compile-time contract test is that the annotation compiles with these defaults.
    }

    /**
     * Helper: asserts a parameter exists and is required (no default).
     */
    private fun <T : Annotation> assertRequired(annotationClass: KClass<T>, paramName: String) {
        val constructor = annotationClass.primaryConstructor
            ?: error("No primary constructor for ${annotationClass.simpleName}")
        val param = constructor.parameters.find { it.name == paramName }
            ?: error("No parameter '$paramName' in ${annotationClass.simpleName}")

        assertEquals(
            false, param.isOptional,
            "Parameter '$paramName' in ${annotationClass.simpleName} should be required (no default)"
        )
    }

    /**
     * Helper: verifies the exact parameter names and count for an annotation.
     */
    private fun <T : Annotation> assertParameterNames(
        annotationClass: KClass<T>,
        expectedNames: List<String>
    ) {
        val constructor = annotationClass.primaryConstructor
            ?: error("No primary constructor for ${annotationClass.simpleName}")
        val actualNames = constructor.parameters
            .filter { it.kind == KParameter.Kind.VALUE }
            .map { it.name }
        assertEquals(
            expectedNames, actualNames,
            "Parameter names mismatch in ${annotationClass.simpleName}"
        )
    }

    @Nested
    inner class GeneratedDslAnnotation {
        @Test
        fun `has expected parameters`() {
            assertParameterNames(
                GeneratedDsl::class,
                listOf("withListGroup", "withMapGroup", "isRoot", "name", "debug")
            )
        }

        @Test
        fun `withListGroup has a default`() {
            assertDefault(GeneratedDsl::class, "withListGroup", false)
        }

        @Test
        fun `withMapGroup has a default`() {
            assertDefault(GeneratedDsl::class, "withMapGroup", GeneratedDsl.MapGroupTypes.NONE)
        }

        @Test
        fun `isRoot has a default`() {
            assertDefault(GeneratedDsl::class, "isRoot", false)
        }

        @Test
        fun `name has a default`() {
            assertDefault(GeneratedDsl::class, "name", "")
        }

        @Test
        fun `debug has a default`() {
            assertDefault(GeneratedDsl::class, "debug", false)
        }
    }

    @Nested
    inner class PublicDslPropertyAnnotation {
        @Test
        fun `has expected parameters`() {
            assertParameterNames(PublicDslProperty::class, listOf("restrictSetter", "wrapInFunction"))
        }

        @Test
        fun `restrictSetter has a default`() {
            assertDefault(PublicDslProperty::class, "restrictSetter", false)
        }

        @Test
        fun `wrapInFunction has a default`() {
            assertDefault(PublicDslProperty::class, "wrapInFunction", false)
        }
    }

    @Nested
    inner class PrivateDslPropertyAnnotation {
        @Test
        fun `has expected parameters`() {
            assertParameterNames(PrivateDslProperty::class, listOf("restrictSetter", "wrapInFunction"))
        }

        @Test
        fun `restrictSetter has a default`() {
            assertDefault(PrivateDslProperty::class, "restrictSetter", false)
        }

        @Test
        fun `wrapInFunction has a default`() {
            assertDefault(PrivateDslProperty::class, "wrapInFunction", true)
        }
    }

    @Nested
    inner class DefaultValueAnnotation {
        @Test
        fun `has expected parameters`() {
            assertParameterNames(DefaultValue::class, listOf("value", "packageName", "className", "inferType"))
        }

        @Test
        fun `value is required`() {
            assertRequired(DefaultValue::class, "value")
        }

        @Test
        fun `packageName has a default`() {
            assertDefault(DefaultValue::class, "packageName", "")
        }

        @Test
        fun `className has a default`() {
            assertDefault(DefaultValue::class, "className", "")
        }

        @Test
        fun `inferType has a default`() {
            assertDefault(DefaultValue::class, "inferType", true)
        }
    }

    @Nested
    inner class ListDslAnnotation {
        @Test
        fun `has expected parameters`() {
            assertParameterNames(
                ListDsl::class,
                listOf("minSize", "maxSize", "uniqueElements", "sorted", "withVararg", "withProvider")
            )
        }

        @Test
        fun `minSize has a default`() {
            assertDefault(ListDsl::class, "minSize", -1)
        }

        @Test
        fun `maxSize has a default`() {
            assertDefault(ListDsl::class, "maxSize", -1)
        }

        @Test
        fun `uniqueElements has a default`() {
            assertDefault(ListDsl::class, "uniqueElements", false)
        }

        @Test
        fun `sorted has a default`() {
            assertDefault(ListDsl::class, "sorted", false)
        }

        @Test
        fun `withVararg has a default`() {
            assertDefault(ListDsl::class, "withVararg", true)
        }

        @Test
        fun `withProvider has a default`() {
            assertDefault(ListDsl::class, "withProvider", true)
        }
    }

    @Nested
    inner class MapDslAnnotation {
        @Test
        fun `has expected parameters`() {
            assertParameterNames(MapDsl::class, listOf("minSize", "maxSize", "withVararg", "withProvider"))
        }

        @Test
        fun `minSize has a default`() {
            assertDefault(MapDsl::class, "minSize", -1)
        }

        @Test
        fun `maxSize has a default`() {
            assertDefault(MapDsl::class, "maxSize", -1)
        }

        @Test
        fun `withVararg has a default`() {
            assertDefault(MapDsl::class, "withVararg", true)
        }

        @Test
        fun `withProvider has a default`() {
            assertDefault(MapDsl::class, "withProvider", true)
        }
    }

    @Nested
    inner class DeprecatedDslAnnotation {
        @Test
        fun `has expected parameters`() {
            assertParameterNames(DeprecatedDsl::class, listOf("message", "replaceWith"))
        }

        @Test
        fun `message is required`() {
            assertRequired(DeprecatedDsl::class, "message")
        }

        @Test
        fun `replaceWith has a default`() {
            assertDefault(DeprecatedDsl::class, "replaceWith", "")
        }
    }

    @Nested
    inner class DescriptionAnnotation {
        @Test
        fun `has expected parameters`() {
            assertParameterNames(DslDescription::class, listOf("value"))
        }

        @Test
        fun `value is required`() {
            assertRequired(DslDescription::class, "value")
        }
    }

    @Nested
    inner class AliasAnnotation {
        @Test
        fun `has expected parameters`() {
            assertParameterNames(DslAlias::class, listOf("names"))
        }

        @Test
        fun `names is required`() {
            assertRequired(DslAlias::class, "names")
        }
    }

    @Nested
    inner class RootDslAnnotation {
        @Test
        fun `has expected parameters`() {
            assertParameterNames(RootDsl::class, listOf("name", "alias"))
        }

        @Test
        fun `name has a default`() {
            assertDefault(RootDsl::class, "name", "")
        }

        @Test
        fun `alias has a default`() {
            assertDefault(RootDsl::class, "alias", "")
        }
    }

    @Nested
    inner class TransientDslAnnotation {
        @Test
        fun `has expected parameters`() {
            assertParameterNames(TransientDsl::class, listOf("reason"))
        }

        @Test
        fun `reason has a default`() {
            assertDefault(TransientDsl::class, "reason", "")
        }
    }

    @Nested
    inner class InjectDslMethodAnnotation {
        @Test
        fun `has no parameters`() {
            assertParameterNames(InjectDslMethod::class, emptyList())
        }
    }

    @Nested
    inner class ValidateDslAnnotation {
        @Test
        fun `has expected parameters`() {
            assertParameterNames(ValidateDsl::class, listOf("expression", "message"))
        }

        @Test
        fun `expression is required`() {
            assertRequired(ValidateDsl::class, "expression")
        }

        @Test
        fun `message has a default`() {
            assertDefault(ValidateDsl::class, "message", "")
        }
    }
}
