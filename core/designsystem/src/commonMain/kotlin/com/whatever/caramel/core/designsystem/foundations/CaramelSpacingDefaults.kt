package com.whatever.caramel.core.designsystem.foundations

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal enum class CaramelSpacingDefaults(
    val spacing: Dp,
) {
    SPACING_XXS(spacing = 2.dp),
    SPACING_XS(spacing = 4.dp),
    SPACING_S(spacing = 8.dp),
    SPACING_M(spacing = 12.dp),
    SPACING_LG(spacing = 16.dp),
    SPACING_XL(spacing = 20.dp),
    SPACING_XXL(spacing = 24.dp),
}
