package com.whatever.caramel.feature.memo.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Preview(showBackground = true)
@Composable
fun MemoItemPreview(modifier: Modifier = Modifier) {
    CaramelTheme {
        MemoItem(
            id = 1L,
            title = "",
            content = "",
            categoriesText = "",
            createdDateText = "",
        ) { }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyMemoPreview(modifier: Modifier = Modifier) {
    CaramelTheme {
        EmptyMemo()
    }
}