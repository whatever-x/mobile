package com.whatever.caramel.core.designsystem.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Preview(widthDp = 600)
@Composable
private fun CaramelTypographyPreview() {
    CaramelTheme {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(3.dp, Alignment.CenterVertically),
        ) {
            val styles =
                mapOf(
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
                    text = "Whatever x Caramel \n" + style.value,
                    textAlign = TextAlign.Center,
                    style = style.key,
                    color = Color.Black,
                )
            }
        }
    }
}
