package com.whatever.caramel

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.whatever.caramel.core.designsystem.themes.CaramelShapePreview
import com.whatever.caramel.core.designsystem.themes.CaramelSpacingPreview

@Preview(widthDp = 300, heightDp = 300, showBackground = true)
@Composable
private fun SpacingPreview() {
    CaramelSpacingPreview()
}

@Preview(widthDp = 300, showBackground = true)
@Composable
private fun ShapePreview() {
    CaramelShapePreview()
}
