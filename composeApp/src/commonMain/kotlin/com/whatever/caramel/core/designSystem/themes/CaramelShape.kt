package com.whatever.caramel.core.designSystem.themes

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import com.whatever.caramel.core.designSystem.foundations.CaramelShapeDefaults

@Immutable
data class CaramelShape(
    val extraSmall: Shape,
    val small: Shape,
    val medium: Shape,
    val large: Shape,
    val extraLarge: Shape,
) {
    companion object {
        fun defaultRadius(): CaramelShape = CaramelShape (
            extraSmall = CaramelShapeDefaults.RADIUS_XS.shape,
            small = CaramelShapeDefaults.RADIUS_S.shape,
            medium = CaramelShapeDefaults.RADIUS_M.shape,
            large = CaramelShapeDefaults.RADIUS_L.shape,
            extraLarge = CaramelShapeDefaults.RADIUS_XL.shape,
        )
    }
}