package com.whatever.caramel.feature.memo.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.memo.MemoScreen
import com.whatever.caramel.feature.memo.mvi.MemoState

@Preview(device = "spec:width=720px,height=1280px,dpi=320")
@Preview(device = "id:pixel_9_pro")
@Composable
private fun MemoScreenPreview(
    @PreviewParameter(MemoScreenPreviewProvider::class) data: MemoState,
) {
    CaramelTheme {
        MemoScreen(state = data) { }
    }
}