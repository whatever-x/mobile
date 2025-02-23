package com.whatever.caramel.core.designSystem.themes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import com.whatever.caramel.core.designSystem.foundations.IbmPlexSans

val LocalCaramelTypography = compositionLocalOf<CaramelTypography> { error("Typography Error") }

val LocalCaramelShape = compositionLocalOf<CaramelShape> { error("Shape Error") }

val LocalCaramelSpacing = compositionLocalOf<CaramelSpacing> { error("Spacing Error") }

@Composable
fun CaramelTheme(
    typography: CaramelTypography = CaramelTypography.defaultTypography(fontFamily = IbmPlexSans()),
    shape: CaramelShape = CaramelShape.defaultRadius(),
    spacing: CaramelSpacing = CaramelSpacing.defaultSpacing(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalCaramelTypography provides typography,
        LocalCaramelShape provides shape,
        LocalCaramelSpacing provides spacing
    ) {
        content.invoke()
    }
}

object CaramelTheme {

    val typography: CaramelTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalCaramelTypography.current

    val shape: CaramelShape
        @Composable
        @ReadOnlyComposable
        get() = LocalCaramelShape.current

    val spacing: CaramelSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalCaramelSpacing.current

}