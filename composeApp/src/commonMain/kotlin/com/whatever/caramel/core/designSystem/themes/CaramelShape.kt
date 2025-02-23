package com.whatever.caramel.core.designSystem.themes

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import com.whatever.caramel.core.designSystem.foundations.CaramelShapeDefaults

@Immutable
data class CaramelShape(
    val xxs: Shape,
    val xs: Shape,
    val s: Shape,
    val m: Shape,
    val l: Shape,
    val xl: Shape,
) {
    companion object {
        fun defaultRadius(): CaramelShape = CaramelShape (
            xxs = CaramelShapeDefaults.RADIUS_XXS.shape,
            xs = CaramelShapeDefaults.RADIUS_XS.shape,
            s = CaramelShapeDefaults.RADIUS_S.shape,
            m = CaramelShapeDefaults.RADIUS_M.shape,
            l = CaramelShapeDefaults.RADIUS_L.shape,
            xl = CaramelShapeDefaults.RADIUS_XL.shape,
        )
    }
}