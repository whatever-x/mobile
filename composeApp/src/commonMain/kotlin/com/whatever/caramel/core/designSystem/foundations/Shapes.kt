package com.whatever.caramel.core.designSystem.foundations

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

internal enum class CaramelShapeDefaults(val shape: Shape) {
    RADIUS_XXS(shape = RoundedCornerShape(2.dp)),
    RADIUS_XS(shape = RoundedCornerShape(4.dp)),
    RADIUS_S(shape = RoundedCornerShape(8.dp)),
    RADIUS_M(shape = RoundedCornerShape(12.dp)),
    RADIUS_L(shape = RoundedCornerShape(16.dp)),
    RADIUS_XL(shape = RoundedCornerShape(24.dp)),
    ;
}