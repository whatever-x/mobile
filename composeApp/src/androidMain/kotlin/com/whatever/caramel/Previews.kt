package com.whatever.caramel

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.whatever.caramel.core.designSystem.themes.CaramelShapePreview
import com.whatever.caramel.core.designSystem.themes.CaramelSpacingPreview
import com.whatever.caramel.core.designSystem.themes.CaramelTypographyPreview


@Preview(widthDp = 600, heightDp = 1000)
@Composable
private fun TypographyPreview() {
    CaramelTypographyPreview()
}

@Preview(widthDp = 300, heightDp = 300)
@Composable
private fun SpacingPreview() {
    CaramelSpacingPreview()
}

@Preview(widthDp = 300)
@Composable
private fun ShapePreview() {
    CaramelShapePreview()
}