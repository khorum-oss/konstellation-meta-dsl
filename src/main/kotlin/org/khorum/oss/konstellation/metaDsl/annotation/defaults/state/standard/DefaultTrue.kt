package org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.standard

import org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.DefaultState
import org.khorum.oss.konstellation.metaDsl.annotation.defaults.state.DefaultStateType

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
@DefaultState(DefaultStateType.TRUE)
annotation class DefaultTrue(
    val negationFunctionName: String = "",
    val negationTemplate: NegationFunctionTemplate = NegationFunctionTemplate.NOT,
    val validFunctionName: String = "",
    val validTemplate: ValidFunctionTemplate = ValidFunctionTemplate.SELF
)
