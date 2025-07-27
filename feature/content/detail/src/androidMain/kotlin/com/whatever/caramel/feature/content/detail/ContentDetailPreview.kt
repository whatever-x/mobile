package com.whatever.caramel.feature.content.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.content.detail.mvi.ContentDetailState

@Composable
@Preview
private fun ContentDetailScreenPreview(
    @PreviewParameter(ContentDetailPreviewParameter::class) data: ContentDetailState,
) {
    CaramelTheme {
        ContentDetailScreen(
            state = data,
            onIntent = {},
        )
    }
}
