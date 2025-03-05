package com.whatever.caramel.core.designsystem.themes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whatever.caramel.core.designsystem.foundations.CaramelShapeDefaults

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

@Composable
fun CaramelShapePreview() {
    CaramelTheme {
        val shapes = mapOf(
            CaramelTheme.shape.xxs to "XXS",
            CaramelTheme.shape.xs to "XS",
            CaramelTheme.shape.s to "S",
            CaramelTheme.shape.m to "M",
            CaramelTheme.shape.l to "L",
            CaramelTheme.shape.xl to "XL",
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (shape in shapes.entries) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = shape.key
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = shape.value,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}