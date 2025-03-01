package com.whatever.caramel.feat.profile.create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.whatever.caramel.feat.profile.create.mvi.ProfileCreateIntent
import com.whatever.caramel.feat.profile.create.mvi.ProfileCreateState

@Composable
internal fun ProfileCreateScreen(
    state: ProfileCreateState,
    onIntent: (ProfileCreateIntent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            modifier = Modifier.align(alignment = Alignment.TopStart),
            onClick = { onIntent(ProfileCreateIntent.ClickBackButton) }
        ) {
            Text(
                text = "뒤로가기",
                fontSize = 12.sp
            )
        }

        Text(
            modifier = Modifier.align(alignment = Alignment.Center),
            text = "프로필 생성 화면입니다.",
            fontSize = 32.sp
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter),
            onClick = { onIntent(ProfileCreateIntent.ClickCreateButton) }
        ) {
            Text(
                text = "프로필 생성 완료 하기",
                fontSize = 18.sp
            )
        }
    }
}

