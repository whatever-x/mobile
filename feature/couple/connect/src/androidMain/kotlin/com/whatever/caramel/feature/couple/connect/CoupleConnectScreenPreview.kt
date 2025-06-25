package com.whatever.caramel.feature.couple.connect

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.couple.connect.mvi.CoupleConnectState

@Preview
@Composable
private fun CoupleConnectScreenPreview(
    @PreviewParameter(CoupleConnectScreenPreviewProvider::class) data: CoupleConnectState
) {
    CaramelTheme {
        CoupleConnectScreen(
            state = data,
            onIntent = { }
        )
    }
}