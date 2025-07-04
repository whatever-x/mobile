package com.whatever.caramel.core.designsystem.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Composable
fun Modifier.shimmer(
    colorList: List<Color> =
        listOf(
            CaramelTheme.color.skeleton.primary,
            CaramelTheme.color.skeleton.secondary,
            CaramelTheme.color.skeleton.primary,
        ),
    shape: Shape = RectangleShape,
): Modifier {
    val transition = rememberInfiniteTransition()
    val translateAnim =
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 1200, delayMillis = 300),
                    repeatMode = RepeatMode.Restart,
                ),
        )
    return this.background(
        brush =
            Brush.linearGradient(
                colors = colorList,
                start = Offset.Zero,
                end = Offset(x = translateAnim.value, y = translateAnim.value),
            ),
        shape = shape,
    )
}
