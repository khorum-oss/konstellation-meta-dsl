package org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.standard

import org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.DefaultState
import org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.DefaultStateType

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
@DefaultState(DefaultStateType.TRUE)
annotation class DefaultFalse(
    val validFunctionName: String = "",
    val validTemplate: ValidFunctionTemplate = ValidFunctionTemplate.NONE
) {
    enum class ValidFunctionTemplate(val template: String) {
        NONE(""),

        /**
         * is{x}
         */
        IS("is{x}"),
        /**
         * does{x}
         */
        DOES("does{x}"),
        /**
         * has{x}
         */
        HAS("has{x}"),
        /**
         * {x}Enabled
         */
        ENABLED("{x}Enabled"),
        /**
         * {x}IsEnabled
         */
        IS_ENABLED("{x}IsEnabled"),
        /**
         * with{x}
         */
        WITH("with{x}"),
        /**
         * {x}Present
         */
        PRESENT("{x}Present"),
        /**
         * {x}IsPresent
         */
        IS_PRESENT("{x}IsPresent"),
        /**
         * always{X}
         */
        ALWAYS("always{x}")
    }
}
