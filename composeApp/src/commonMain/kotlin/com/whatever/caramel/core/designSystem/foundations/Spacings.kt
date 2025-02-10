package com.whatever.caramel.core.designSystem.foundations

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// @ham2174 TODO : spacing 확정 시 변경
internal enum class CaramelSpacingDefaults(val spacing: Dp) {
    SPACING_XXL(spacing = 32.dp),
    SPACING_XL(spacing = 24.dp),
    SPACING_LG(spacing = 20.dp),
    SPACING_MD(spacing = 16.dp),
    SPACING_SM(spacing = 12.dp),
    SPACING_XS(spacing = 8.dp),
    SPACING_XXS(spacing = 4.dp),
    ;
}
