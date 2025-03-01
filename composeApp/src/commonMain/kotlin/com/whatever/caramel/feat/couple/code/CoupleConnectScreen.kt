package com.whatever.caramel.feat.couple.code

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.whatever.caramel.feat.couple.code.mvi.CoupleConnectIntent
import com.whatever.caramel.feat.couple.code.mvi.CoupleConnectState

@Composable
internal fun CoupleConnectScreen(
    state: CoupleConnectState,
    onIntent: (CoupleConnectIntent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            modifier = Modifier.align(alignment = Alignment.TopStart),
            onClick = { onIntent(CoupleConnectIntent.ClickBackButton) }
        ) {
            Text(
                text = "뒤로가기",
                fontSize = 12.sp
            )
        }

        Text(
            modifier = Modifier.align(alignment = Alignment.Center),
            text = "코드 입력 화면 입니다.",
            fontSize = 32.sp
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter),
            onClick = { onIntent(CoupleConnectIntent.ClickConnectButton) }
        ) {
            Text(
                text = "연결하기",
                fontSize = 12.sp
            )
        }
    }
}