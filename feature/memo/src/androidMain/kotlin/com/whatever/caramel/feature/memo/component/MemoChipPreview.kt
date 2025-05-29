package com.whatever.caramel.feature.memo.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.entity.Tag

@Preview
@Composable
private fun MemoChipPreview() {
    CaramelTheme {
        MemoChip(
            tag = Tag(1, "label1"),
            isSelected = false
        ) { }
    }
}