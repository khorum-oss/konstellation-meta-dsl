package org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.standard

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