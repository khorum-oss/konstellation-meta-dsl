package org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.standard

import org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.DefaultState
import org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.DefaultStateType

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
@DefaultState(DefaultStateType.TRUE)
annotation class DefaultTrue(
    val negationFunctionName: String = "",
    val negationTemplate: NegationFunctionTemplate = NegationFunctionTemplate.NONE
) {

    enum class NegationFunctionTemplate(val template: String) {
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
}
