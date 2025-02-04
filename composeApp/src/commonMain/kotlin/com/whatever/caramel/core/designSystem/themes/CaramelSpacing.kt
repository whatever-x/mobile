package com.whatever.caramel.core.designSystem.themes

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import com.whatever.caramel.core.designSystem.foundations.CaramelSpacingDefaults

@Immutable
data class CaramelSpacing(
    val doubleExtraLarge: Dp,
    val extraLarge: Dp,
    val large: Dp,
    val medium: Dp,
    val small: Dp,
    val extraSmall: Dp,
    val doubleExtraSmall: Dp,
) {
    companion object {
        fun defaultSpacing(): CaramelSpacing = CaramelSpacing(
            doubleExtraLarge = CaramelSpacingDefaults.SPACING_XXL.spacing,
            extraLarge = CaramelSpacingDefaults.SPACING_XL.spacing,
            large = CaramelSpacingDefaults.SPACING_LG.spacing,
            medium = CaramelSpacingDefaults.SPACING_MD.spacing,
            small = CaramelSpacingDefaults.SPACING_SM.spacing,
            extraSmall = CaramelSpacingDefaults.SPACING_XS.spacing,
            doubleExtraSmall = CaramelSpacingDefaults.SPACING_XXS.spacing,
        )
    }
}
