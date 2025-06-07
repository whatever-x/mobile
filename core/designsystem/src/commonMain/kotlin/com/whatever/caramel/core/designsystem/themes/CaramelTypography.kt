package com.whatever.caramel.core.designsystem.themes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Immutable
data class CaramelTypography(
    val display: TextStyle,
    val heading1: TextStyle,
    val heading2: TextStyle,
    val heading3: TextStyle,
    val body1: Body1Type,
    val body2: Body2Type,
    val body3: Body3Type,
    val body4: Body4Type,
    val label1: Label1Type,
    val label2: Label2Type,
    val label3: Label3Type,
) {
    interface BoldStyle { val bold: TextStyle }
    interface RegularStyle { val regular: TextStyle }
    interface ReadingStyle { val reading: TextStyle }

    interface Body1Type : BoldStyle, RegularStyle
    interface Body2Type : BoldStyle, RegularStyle, ReadingStyle
    interface Body3Type : BoldStyle, RegularStyle, ReadingStyle
    interface Body4Type : BoldStyle, RegularStyle
    interface Label1Type : BoldStyle, RegularStyle
    interface Label2Type : BoldStyle, RegularStyle
    interface Label3Type : BoldStyle, RegularStyle

    @Immutable
    data class Body1Style(
        override val bold: TextStyle,
        override val regular: TextStyle
    ) : Body1Type

    @Immutable
    data class Body2Style(
        override val bold: TextStyle,
        override val regular: TextStyle,
        override val reading: TextStyle
    ) : Body2Type

    @Immutable
    data class Body3Style(
        override val bold: TextStyle,
        override val regular: TextStyle,
        override val reading: TextStyle
    ) : Body3Type

    @Immutable
    data class Body4Style(
        override val bold: TextStyle,
        override val regular: TextStyle,
    ) : Body4Type

    @Immutable
    data class Label1Style(
        override val bold: TextStyle,
        override val regular: TextStyle,
    ) : Label1Type

    @Immutable
    data class Label2Style(
        override val bold: TextStyle,
        override val regular: TextStyle,
    ) : Label2Type

    @Immutable
    data class Label3Style(
        override val bold: TextStyle,
        override val regular: TextStyle,
    ) : Label3Type

    companion object {
        fun defaultTypography(fontFamily: FontFamily): CaramelTypography = CaramelTypography(
            display = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.W600,
                fontSize = 44.sp,
                letterSpacing = 0.sp,
                lineHeight = 56.0f.sp,
            ),
            heading1 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.W600,
                fontSize = 24.sp,
                letterSpacing = 0.sp,
                lineHeight = 32.0f.sp,
            ),
            heading2 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp,
                letterSpacing = 0.sp,
                lineHeight = 28.0f.sp,
            ),
            heading3 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.W600,
                fontSize = 18.sp,
                letterSpacing = 0.sp,
                lineHeight = 26.0f.sp,
            ),
            body1 = Body1Style(
                bold = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    letterSpacing = 0.sp,
                    lineHeight = 24.0f.sp,
                ),
                regular = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight(450),
                    fontSize = 16.sp,
                    letterSpacing = 0.sp,
                    lineHeight = 24.0f.sp,
                ),
            ),
            body2 = Body2Style(
                bold = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W600,
                    fontSize = 15.sp,
                    letterSpacing = 0.sp,
                    lineHeight = 22.0f.sp,
                ),
                regular = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight(450),
                    fontSize = 15.sp,
                    letterSpacing = 0.sp,
                    lineHeight = 22.0f.sp,
                ),
                reading = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight(450),
                    fontSize = 15.sp,
                    letterSpacing = 0.sp,
                    lineHeight = 26.0f.sp,
                ),
            ),
            body3 = Body3Style(
                bold = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W600,
                    fontSize = 14.sp,
                    letterSpacing = 0.sp,
                    lineHeight = 20.0f.sp,
                ),
                regular = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight(450),
                    fontSize = 14.sp,
                    letterSpacing = 0.sp,
                    lineHeight = 20.0f.sp,
                ),
                reading = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight(450),
                    fontSize = 14.sp,
                    letterSpacing = 0.sp,
                    lineHeight = 24.0f.sp,
                ),
            ),
            body4 = Body4Style(
                bold = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W600,
                    fontSize = 13.sp,
                    letterSpacing = 0.sp,
                    lineHeight = 18.0f.sp,
                ),
                regular = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight(450),
                    fontSize = 13.sp,
                    letterSpacing = 0.sp,
                    lineHeight = 18.0f.sp,
                ),
            ),
            label1 = Label1Style(
                bold = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W600,
                    fontSize = 12.sp,
                    letterSpacing = 0.sp,
                    lineHeight = 16.0f.sp,
                ),
                regular = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight(450),
                    fontSize = 12.sp,
                    letterSpacing = 0.sp,
                    lineHeight = 16.0f.sp,
                ),
            ),
            label2 = Label2Style(
                bold = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W600,
                    fontSize = 11.sp,
                    letterSpacing = 0.sp,
                    lineHeight = 14.0f.sp,
                ),
                regular = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight(450),
                    fontSize = 11.sp,
                    letterSpacing = 0.sp,
                    lineHeight = 14.0f.sp,
                ),
            ),
            label3 = Label3Style(
                bold = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.W600,
                    fontSize = 10.sp,
                    letterSpacing = 0.sp,
                    lineHeight = 13.0f.sp,
                ),
                regular = TextStyle(
                    fontFamily = fontFamily,
                    fontWeight = FontWeight(450),
                    fontSize = 10.sp,
                    letterSpacing = 0.sp,
                    lineHeight = 13.0f.sp,
                ),
            ),
        )
    }
}
