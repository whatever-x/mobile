package com.whatever.caramel.feature.profile.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.profile.edit.component.EditBirthday
import com.whatever.caramel.feature.profile.edit.component.EditNickname
import com.whatever.caramel.feature.profile.edit.component.EditStartDate
import com.whatever.caramel.feature.profile.edit.component.ProfileEditBottomBar
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditIntent
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditState
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditType
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun ProfileEditScreen(
    state: ProfileEditState,
    onIntent: (ProfileEditIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            CaramelTopBar(
                modifier =
                    Modifier
                        .statusBarsPadding(),
                trailingIcon = {
                    Icon(
                        modifier =
                            Modifier.clickable(
                                interactionSource = null,
                                indication = null,
                                onClick = { onIntent(ProfileEditIntent.ClickCloseButton) },
                            ),
                        painter = painterResource(Resources.Icon.ic_cancel_24),
                        contentDescription = null,
                    )
                },
            )
        },
        bottomBar = {
            ProfileEditBottomBar(
                modifier =
                    Modifier
                        .navigationBarsPadding()
                        .imePadding(),
                buttonEnabled = state.isSaveButtonEnabled,
                buttonText = "저장하기",
                onClickButton = { onIntent(ProfileEditIntent.ClickSaveButton) },
            )
        },
        containerColor = CaramelTheme.color.background.primary,
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(paddingValues = innerPadding),
        ) {
            when (state.editUiType) {
                ProfileEditType.NONE -> {}
                ProfileEditType.NICKNAME -> {
                    EditNickname(
                        nickname = state.nickName,
                        onNicknameChange = { onIntent(ProfileEditIntent.ChangeNickname(it)) },
                    )
                }

                ProfileEditType.BIRTHDAY -> {
                    EditBirthday(
                        dateUiState = state.birthDay,
                        onYearChange = { onIntent(ProfileEditIntent.ChangeBirthDayYearPicker(it)) },
                        onMonthChange = { onIntent(ProfileEditIntent.ChangeBirthDayMonthPicker(it)) },
                        onDayChange = { onIntent(ProfileEditIntent.ChangeBirthDayDayPicker(it)) },
                    )
                }

                ProfileEditType.START_DATE -> {
                    EditStartDate(
                        dateUiState = state.startDate,
                        onYearChange = { onIntent(ProfileEditIntent.ChangeDDayYearPicker(it)) },
                        onMonthChange = { onIntent(ProfileEditIntent.ChangeDDayMonthPicker(it)) },
                        onDayChange = { onIntent(ProfileEditIntent.ChangeDDayDayPicker(it)) },
                    )
                }
            }
        }
    }
}
