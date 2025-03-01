package com.whatever.caramel.feat.profile.presentation.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.whatever.caramel.feat.profile.presentation.edit.mvi.ProfileEditType
import com.whatever.caramel.feat.profile.presentation.edit.mvi.ProfileEditIntent
import com.whatever.caramel.feat.profile.presentation.edit.mvi.ProfileEditState

@Composable
internal fun ProfileEditScreen(
    state: ProfileEditState,
    onIntent: (ProfileEditIntent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            modifier = Modifier.align(alignment = Alignment.TopStart),
            onClick = { onIntent(ProfileEditIntent.ClickCloseButton) }
        ) {
            Text(
                text = "닫기",
                fontSize = 12.sp
            )
        }

        Text(
            modifier = Modifier.align(alignment = Alignment.Center),
            text = when (state.editUiType) {
                ProfileEditType.D_DAY -> "디데이 수정 화면 입니다."
                ProfileEditType.BIRTHDAY -> "생일 수정 화면 입니다."
                ProfileEditType.NICK_NAME -> "닉네임 수정 화면 입니다."
            },
            fontSize = 32.sp
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter),
            onClick = { onIntent(ProfileEditIntent.ClickSaveButton) }
        ) {
            Text(
                text = "저장하기",
                fontSize = 18.sp
            )
        }
    }
}