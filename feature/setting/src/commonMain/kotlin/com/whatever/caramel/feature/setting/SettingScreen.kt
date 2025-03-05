package com.whatever.caramel.feature.setting

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.whatever.caramel.feature.setting.mvi.SettingIntent
import com.whatever.caramel.feature.setting.mvi.SettingState

@Composable
internal fun SettingScreen(
    state: SettingState,
    onIntent: (SettingIntent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            modifier = Modifier.align(alignment = Alignment.TopStart),
            onClick = { onIntent(SettingIntent.ClickBackButton) }
        ) {
            Text(
                text = "뒤로가기",
                fontSize = 12.sp
            )
        }

        Text(
            modifier = Modifier.align(alignment = Alignment.Center),
            text = "설정 화면입니다.",
            fontSize = 32.sp
        )

        Button(
            modifier = Modifier.align(alignment = Alignment.BottomStart),
            onClick = { onIntent(SettingIntent.ClickEditNicknameButton) }
        ) {
            Text(
                text = "바텀 시트 닉네임 수정 하기",
                fontSize = 12.sp
            )
        }

        Button(
            modifier = Modifier.align(alignment = Alignment.BottomEnd),
            onClick = { onIntent(SettingIntent.ClickEditBirthdayButton) }
        ) {
            Text(
                text = "바텀 시트 생일 수정 하기",
                fontSize = 12.sp
            )
        }
    }
}