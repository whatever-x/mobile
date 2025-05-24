package com.whatever.caramel.feature.memo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.whatever.caramel.feature.memo.mvi.MemoIntent
import com.whatever.caramel.feature.memo.mvi.MemoState

@Composable
internal fun MemoScreen(
    state: MemoState,
    onIntent: (MemoIntent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.align(alignment = Alignment.Center),
            text = "메모 화면 입니다.",
            fontSize = 32.sp
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(
                    alignment = Alignment.BottomCenter
                ),
            onClick = { onIntent(MemoIntent.ClickMemo(memoId = 1L)) } // @RyuSw-cs 2025.05.19 FIXME :임시 메모 아이디, 구현 시 제거 필요
        ) {
            Text(
                text = "메모 아이템",
                fontSize = 12.sp
            )
        }
    }
}