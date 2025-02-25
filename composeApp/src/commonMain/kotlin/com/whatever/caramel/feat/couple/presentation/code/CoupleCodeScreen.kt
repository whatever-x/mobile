package com.whatever.caramel.feat.couple.presentation.code

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.whatever.caramel.feat.couple.presentation.code.mvi.CoupleCodeIntent
import com.whatever.caramel.feat.couple.presentation.code.mvi.CoupleCodeState

@Composable
internal fun CoupleCodeScreen(
    state: CoupleCodeState,
    onIntent: (CoupleCodeIntent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.align(alignment = Alignment.Center),
            text = "코드 입력 화면 입니다.",
            fontSize = 32.sp
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter),
            onClick = { onIntent(CoupleCodeIntent.ClickConnectButton) }
        ) {
            Text(
                text = "연결하기",
                fontSize = 12.sp
            )
        }
    }
}