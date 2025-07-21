package com.whatever.caramel.feature.memo.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.memo.MemoScreen
import com.whatever.caramel.feature.memo.mvi.MemoState

@Preview
@Composable
private fun MemoScreenPreview(
    @PreviewParameter(MemoScreenPreviewProvider::class) data: MemoState,
) {
    CaramelTheme {
        MemoScreen(state = data) { }
    }
}
