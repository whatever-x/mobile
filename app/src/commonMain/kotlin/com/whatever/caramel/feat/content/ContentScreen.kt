package com.whatever.caramel.feat.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.whatever.caramel.feat.content.mvi.ContentIntent
import com.whatever.caramel.feat.content.mvi.ContentState

@Composable
internal fun ContentScreen(
    state: ContentState,
    onIntent: (ContentIntent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.align(alignment = Alignment.Center),
            text = "컨텐츠 생성/수정 화면 입니다.",
            fontSize = 32.sp
        )

        Button(
            modifier = Modifier.align(alignment = Alignment.TopEnd),
            onClick = { onIntent(ContentIntent.ClickCloseButton) }
        ) {
            Text(
                text = "닫기",
                fontSize = 12.sp
            )
        }

        Row(
            modifier = Modifier.align(alignment = Alignment.BottomCenter)
        ) {
            Button(
                onClick = { onIntent(ContentIntent.ClickDeleteButton) }
            ) {
                Text(
                    text = "삭제하기",
                    fontSize = 12.sp
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onIntent(ContentIntent.ClickSaveButton) }
            ) {
                Text(
                    text = "저장하기",
                    fontSize = 12.sp
                )
            }
        }
    }
}