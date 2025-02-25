package com.whatever.caramel.core.designSystem.themes

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
import org.jetbrains.compose.ui.tooling.preview.Preview

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

@Composable
fun CaramelTypographyPreview() {
    CaramelTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(3.dp, Alignment.CenterVertically)
        ) {
            val styles = mapOf(
                CaramelTheme.typography.display to "Display",
                CaramelTheme.typography.heading1 to "heading1",
                CaramelTheme.typography.heading2 to "heading2",
                CaramelTheme.typography.heading3 to "heading3",
                CaramelTheme.typography.body1.bold to "body1.bold",
                CaramelTheme.typography.body1.regular to "body1.regular",
                CaramelTheme.typography.body2.bold to "body2.bold",
                CaramelTheme.typography.body2.regular to "body2.regular",
                CaramelTheme.typography.body2.reading to "body2.reading",
                CaramelTheme.typography.body3.bold to "body3.bold",
                CaramelTheme.typography.body3.regular to "body3.regular",
                CaramelTheme.typography.body3.reading to "body3.reading",
                CaramelTheme.typography.body4.bold to "body4.bold",
                CaramelTheme.typography.body4.regular to "body4.regular",
                CaramelTheme.typography.label1.bold to "label1.bold",
                CaramelTheme.typography.label1.regular to "label1.regular",
                CaramelTheme.typography.label2.bold to "label2.bold",
                CaramelTheme.typography.label2.regular to "label2.regular",
                CaramelTheme.typography.label3.bold to "label3.bold",
                CaramelTheme.typography.label3.regular to "label3.regular",
            )

            for (style in styles.entries) {
                Text(
                    text = "Whatever x Caramel \n" + "${style.value}",
                    textAlign = TextAlign.Center,
                    style = style.key,
                    color = Color.Black
                )
            }
        }
    }
}