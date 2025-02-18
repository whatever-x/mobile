package com.whatever.caramel.feat.temp

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// @RyuSw-cs : 2025.02.10 샘플 Navigation 버튼
@Composable
fun SampleNavigateButton(
    modifier: Modifier = Modifier,
    content: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = content
        )
    }
}