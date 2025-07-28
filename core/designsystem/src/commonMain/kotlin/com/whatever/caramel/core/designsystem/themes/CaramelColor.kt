package com.whatever.caramel.core.designsystem.themes

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.whatever.caramel.core.designsystem.foundations.Alpha100
import com.whatever.caramel.core.designsystem.foundations.Green200
import com.whatever.caramel.core.designsystem.foundations.Green600
import com.whatever.caramel.core.designsystem.foundations.Green700
import com.whatever.caramel.core.designsystem.foundations.Neutral100
import com.whatever.caramel.core.designsystem.foundations.Neutral200
import com.whatever.caramel.core.designsystem.foundations.Neutral300
import com.whatever.caramel.core.designsystem.foundations.Neutral400
import com.whatever.caramel.core.designsystem.foundations.Neutral600
import com.whatever.caramel.core.designsystem.foundations.Neutral900
import com.whatever.caramel.core.designsystem.foundations.Orange100
import com.whatever.caramel.core.designsystem.foundations.Orange200
import com.whatever.caramel.core.designsystem.foundations.Orange300
import com.whatever.caramel.core.designsystem.foundations.Orange400
import com.whatever.caramel.core.designsystem.foundations.Orange500
import com.whatever.caramel.core.designsystem.foundations.Orange700
import com.whatever.caramel.core.designsystem.foundations.Red500
import com.whatever.caramel.core.designsystem.foundations.White100
import com.whatever.caramel.core.designsystem.themes.CaramelColor.TextColor as TextColor1

@Immutable
data class CaramelColor(
    val text: TextColor,
    val background: BackgroundColor,
    val alpha: DimColor,
    val fill: FillColor,
    val divider: DividerColor,
    val icon: IconColor,
    val skeleton: SkeletonColor,
) {
    @Immutable
    data class TextColor(
        val primary: Color = Neutral900,
        val secondary: Color = Neutral600,
        val tertiary: Color = Neutral400,
        val brand: Color = Orange500,
        val inverse: Color = White100,
        val disabledPrimary: Color = Neutral400,
        val disabledBrand: Color = Orange400,
        val placeholder: Color = Neutral400,
        val labelBrand: Color = Orange700,
        val labelAccent1: Color = Red500,
        val labelAccent2: Color = Green600,
        val labelAccent3: Color = Neutral900,
        val labelAccent4: Color = Green700,
    )

    @Immutable
    data class BackgroundColor(
        val primary: Color = Neutral100,
        val secondary: Color = Orange100,
        val tertiary: Color = White100,
    )

    @Immutable
    data class DimColor(
        val primary: Color = Alpha100,
    )

    @Immutable
    data class FillColor(
        val primary: Color = Neutral900,
        val secondary: Color = Neutral600,
        val tertiary: Color = Neutral400,
        val quaternary: Color = Neutral200,
        val quinary: Color = Orange200,
        val inverse: Color = White100,
        val brand: Color = Orange500,
        val disabledPrimary: Color = Neutral200,
        val disabledBrand: Color = Orange200,
        val labelBrand: Color = Orange300,
        val accent1: Color = Green600,
        val labelAccent1: Color = Red500,
        val labelAccent2: Color = Orange500,
        val labelAccent3: Color = Green200,
        val labelAccent4: Color = Neutral300,
    )

    @Immutable
    data class DividerColor(
        val primary: Color = Neutral200,
        val secondary: Color = White100,
        val tertiary: Color = Neutral300,
    )

    @Immutable
    data class IconColor(
        val primary: Color = Neutral900,
        val secondary: Color = Neutral600,
        val tertiary: Color = Neutral400,
        val brand: Color = Orange500,
        val inverse: Color = White100,
        val disabledPrimary: Color = Neutral400,
        val disabledBrand: Color = Orange400,
    )

    @Immutable
    data class SkeletonColor(
        val primary: Color = Neutral200,
        val secondary: Color = Neutral100,
    )

    companion object {
        fun defaultColor(): CaramelColor =
            CaramelColor(
                text = TextColor1(),
                background = BackgroundColor(),
                alpha = DimColor(),
                fill = FillColor(),
                divider = DividerColor(),
                icon = IconColor(),
                skeleton = SkeletonColor(),
            )
    }
}
