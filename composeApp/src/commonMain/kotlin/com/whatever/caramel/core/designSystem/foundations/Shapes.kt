package com.whatever.caramel.core.designSystem.foundations

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

// @ham2174 TODO : spacing 확정 시 변경
internal enum class CaramelShapeDefaults(val shape: Shape) {
    RADIUS_XS(shape = RoundedCornerShape(8.dp)),
    RADIUS_S(shape = RoundedCornerShape(12.dp)),
    RADIUS_M(shape = RoundedCornerShape(16.dp)),
    RADIUS_L(shape = RoundedCornerShape(20.dp)),
    RADIUS_XL(shape = RoundedCornerShape(24.dp)),
    ;
}