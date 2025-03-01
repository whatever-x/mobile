package com.whatever.caramel.feat.couple.invite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.whatever.caramel.feat.couple.invite.mvi.CoupleInviteIntent
import com.whatever.caramel.feat.couple.invite.mvi.CoupleInviteState

@Composable
internal fun CoupleInviteScreen(
    state: CoupleInviteState,
    onIntent: (CoupleInviteIntent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            modifier = Modifier.align(alignment = Alignment.TopEnd),
            onClick = { onIntent(CoupleInviteIntent.ClickCloseButton) }
        ) {
            Text(
                text = "닫기"
            )
        }

        Text(
            modifier = Modifier.align(alignment = Alignment.Center),
            text = "초대 화면 입니다.",
            fontSize = 32.sp
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter),
            onClick = { onIntent(CoupleInviteIntent.ClickConnectCoupleButton) }
        ) {
            Text(
                text = "코드 입력 하러 가기"
            )
        }
    }
}