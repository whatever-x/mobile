package com.whatever.caramel.core.designSystem.themes

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import com.whatever.caramel.core.designSystem.foundations.CaramelSpacingDefaults

@Immutable
data class CaramelSpacing(
    val xxl: Dp,
    val xl: Dp,
    val l: Dp,
    val m: Dp,
    val s: Dp,
    val xs: Dp,
    val xxs: Dp,
) {
    companion object {
        fun defaultSpacing(): CaramelSpacing = CaramelSpacing(
            xxl = CaramelSpacingDefaults.SPACING_XXL.spacing,
            xl = CaramelSpacingDefaults.SPACING_XL.spacing,
            l = CaramelSpacingDefaults.SPACING_LG.spacing,
            m = CaramelSpacingDefaults.SPACING_M.spacing,
            s = CaramelSpacingDefaults.SPACING_S.spacing,
            xs = CaramelSpacingDefaults.SPACING_XS.spacing,
            xxs = CaramelSpacingDefaults.SPACING_XXS.spacing,
        )
    }
}
