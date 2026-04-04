package org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.standard

import org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.DefaultState
import org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.DefaultStateType

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
@DefaultState(DefaultStateType.TRUE)
annotation class DefaultTrue(
    val negationFunctionName: String = "",
    val negationTemplate: NegationFunctionTemplate = NegationFunctionTemplate.NONE,
    val validFunctionName: String = "",
    val validTemplate: ValidFunctionTemplate = ValidFunctionTemplate.NONE
) {

    enum class NegationFunctionTemplate(val template: String) {
        SELF(""),
        /**
         * NONE
         */
        NONE(""),
        /**
         * doesNot{x}
         */
        DOES_NOT("doesNot{x}"),
        /**
         * doesNotHave{x}
         */
        DOES_NOT_HAVE("doesNotHave{x}"),
        /**
         * {x}IsDisabled
         */
        DISABLED("{x}Disabled"),
        /**
         * {x}Disabled
         */
        IS_DISABLED("{x}IsDisabled"),
        /**
         * not{x}
         */
        NOT("not{x}"),
        /**
         * isNot{X}
         */
        IS_NOT("isNot{x}"),
        /**
         * hasNot{X}
         */
        HAS_NOT("hasNot{x}"),
        /**
         * lacks{X}
         */
        LACKS("lacks{x}"),
        /**
         * no{X}
         */
        NO("no{x}"),
        /**
         * without{X}
         */
        WITHOUT("without{x}"),
        /**
         * {x}Missing
         */
        MISSING("{x}Missing"),
        /**
         * {x}isMissing
         */
        IS_MISSING("{x}IsMissing"),
        /**
         * {x}Absent
         */
        ABSENT("{x}Absent"),
        /**
         * {x}IsAbsent
         */
        IS_ABSENT("{x}IsAbsent"),
        /**
         * never{X}
         */
        NEVER("never{x}");
    }

    enum class ValidFunctionTemplate(val template: String) {
        SELF(""),
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
