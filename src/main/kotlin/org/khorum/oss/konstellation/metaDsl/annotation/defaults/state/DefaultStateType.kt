package org.khorum.oss.konstellation.metaDsl.annotation.defaults.state

/**
 * Predefined default states for common property types.
 *
 * Each entry carries a [codeSnippet] — the exact Kotlin literal the KSP processor
 * should emit as the default value initializer in generated builder code.
 *
 * Use with [@DefaultState][DefaultState] instead of [@DefaultValue][org.khorum.oss.konstellation.metaDsl.annotation.defaults.DefaultValue]
 * to avoid string-escaping issues and improve readability for standard defaults.
 *
 * @property codeSnippet The Kotlin code literal emitted by the processor.
 */
enum class DefaultStateType(val codeSnippet: String) {
    EMPTY_STRING("\"\""),
    ZERO_INT("0"),
    ZERO_LONG("0L"),
    ZERO_DOUBLE("0.0"),
    ZERO_FLOAT("0.0f"),
    EMPTY_LIST("mutableListOf()"),
    EMPTY_MAP("mutableMapOf()"),
    TRUE("true"),
    FALSE("false");

    companion object {
        val NUMERIC_TYPES: List<DefaultStateType> = listOf(ZERO_INT, ZERO_LONG, ZERO_DOUBLE, ZERO_FLOAT)
        val COLLECTION_TYPES: List<DefaultStateType> = listOf(EMPTY_LIST, EMPTY_MAP)
        val BOOLEAN_TYPES: List<DefaultStateType> = listOf(TRUE, FALSE)
    }
}
