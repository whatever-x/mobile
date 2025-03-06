package com.whatever.caramel.core.designsystem.themes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.CaramelSpacingDefaults

@Immutable
data class CaramelSpacing(
    val xxl: Dp,
    val xl: Dp,
    val l: Dp,
    val m: Dp,
    val s: Dp,
    val xs: Dp,
    val xxs: Dp,
) {
    companion object {
        fun defaultSpacing(): CaramelSpacing = CaramelSpacing(
            xxl = CaramelSpacingDefaults.SPACING_XXL.spacing,
            xl = CaramelSpacingDefaults.SPACING_XL.spacing,
            l = CaramelSpacingDefaults.SPACING_LG.spacing,
            m = CaramelSpacingDefaults.SPACING_M.spacing,
            s = CaramelSpacingDefaults.SPACING_S.spacing,
            xs = CaramelSpacingDefaults.SPACING_XS.spacing,
            xxs = CaramelSpacingDefaults.SPACING_XXS.spacing,
        )
    }
}

@Composable
fun CaramelSpacingPreview() {
    CaramelTheme {
        val spacings = listOf(
            CaramelTheme.spacing.xxs,
            CaramelTheme.spacing.xs,
            CaramelTheme.spacing.s,
            CaramelTheme.spacing.m,
            CaramelTheme.spacing.l,
            CaramelTheme.spacing.xl,
            CaramelTheme.spacing.xxl,
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (spacing in spacings.listIterator()) {
                HorizontalDivider(
                    modifier = Modifier.width(30.dp),
                    thickness = spacing,
                    color = Color.Black
                )
            }
        }
    }
}