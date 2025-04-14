@file:OptIn(ExperimentalMaterial3Api::class)

package com.whatever.caramel.feature.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.setting.component.SettingEditProfileBottomSheet
import com.whatever.caramel.feature.setting.component.SettingListText
import com.whatever.caramel.feature.setting.component.SettingTopBar
import com.whatever.caramel.feature.setting.component.SettingUserProfile
import com.whatever.caramel.feature.setting.component.SettingUserProfileSkeleton
import com.whatever.caramel.feature.setting.mvi.SettingIntent
import com.whatever.caramel.feature.setting.mvi.SettingState
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun SettingScreen(
    state: SettingState,
    onIntent: (SettingIntent) -> Unit
) {
    SettingDialogHost(
        state = state,
        onIntent = onIntent
    )
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = CaramelTheme.color.background.primary,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 20.dp)
            ) {
                SettingListText(
                    mainText = "로그아웃",
                    mainTextColor = CaramelTheme.color.text.tertiary,
                    onClickListItem = { onIntent(SettingIntent.ToggleLogout) },
                )

                SettingListText(
                    mainText = "탈퇴하기",
                    mainTextColor = CaramelTheme.color.text.tertiary,
                    onClickListItem = { onIntent(SettingIntent.ToggleUserCancelledButton) },
                )
            }
        },
        topBar = {
            SettingTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                appbarText = "설정",
                onClickBackButton = { onIntent(SettingIntent.ClickSettingBackButton) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .padding(all = CaramelTheme.spacing.xl)
            ) {
                Text(
                    text = "우리의 시작",
                    style = CaramelTheme.typography.heading1,
                    color = CaramelTheme.color.text.primary
                )
                Row(
                    modifier = Modifier
                        .clickable(
                            onClick = { onIntent(SettingIntent.ClickEditCountDownButton) },
                            interactionSource = null,
                            indication = null
                        )
                ) {
                    Text(
                        text = state.startDate,
                        style = CaramelTheme.typography.body2.regular,
                        color = CaramelTheme.color.text.secondary
                    )
                    Icon(
                        modifier = Modifier.padding(
                            all = CaramelTheme.spacing.xs
                        ),
                        painter = painterResource(Resources.Icon.ic_edit_14),
                        tint = CaramelTheme.color.icon.tertiary,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.padding(bottom = 20.dp))
                if (state.isLoading) {
                    SettingUserProfileSkeleton()
                    Spacer(modifier = Modifier.padding(bottom = CaramelTheme.spacing.m))
                    SettingUserProfileSkeleton()
                } else {
                    SettingUserProfile(
                        gender = state.myInfo.gender,
                        nickname = state.myInfo.nickname,
                        birthDay = state.myInfo.birthDate,
                        isEditable = true,
                        onClickEditProfile = { onIntent(SettingIntent.ToggleEditProfile) }
                    )
                    Spacer(modifier = Modifier.padding(bottom = CaramelTheme.spacing.m))
                    SettingUserProfile(
                        gender = state.partnerInfo.gender,
                        nickname = state.partnerInfo.nickname,
                        birthDay = state.partnerInfo.birthDate,
                        isEditable = false
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(
                    top = 10.dp,
                    bottom = 30.dp
                ),
                color = CaramelTheme.color.divider.primary
            )
            Column(
                modifier = Modifier.padding(horizontal = CaramelTheme.spacing.xl)
            ) {
                Text(
                    modifier = Modifier,
                    text = "서비스 소개",
                    style = CaramelTheme.typography.heading3,
                    color = CaramelTheme.color.text.primary
                )
                Spacer(
                    modifier = Modifier.padding(
                        bottom = CaramelTheme.spacing.s
                    )
                )
                SettingListText(
                    mainText = "앱 버전",
                    tailText = "업데이트",
                    mainTextColor = CaramelTheme.color.text.primary,
                    onClickTailText = { onIntent(SettingIntent.ClickAppUpdateButton) }
                )
                SettingListText(
                    mainText = "서비스 이용약관",
                    mainTextColor = CaramelTheme.color.text.primary,
                    onClickListItem = { onIntent(SettingIntent.ClickTermsOfServiceButtons) }
                )
                SettingListText(
                    mainText = "개인정보 처리방침",
                    mainTextColor = CaramelTheme.color.text.primary,
                    onClickListItem = { onIntent(SettingIntent.ClickPrivacyPolicyButton) },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingDialogHost(
    state: SettingState,
    onIntent: (SettingIntent) -> Unit
) {
    if (state.isShowEditProfileBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onIntent(SettingIntent.ToggleEditProfile) },
            dragHandle = null,
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp
            ),
            scrimColor = CaramelTheme.color.alpha.primary
        ) {
            SettingEditProfileBottomSheet(
                navigateToProfileEditNickName = { onIntent(SettingIntent.ClickEditNicknameButton) },
                navigateToProfileEditBrithDay = { onIntent(SettingIntent.ClickEditBirthDayButton) }
            )
        }
    }

    if (state.isShowLogoutDialog) {
        // TODO : 다이얼로그 컴포넌트 추가후 구현
    }

    if (state.isShowUserCancelledDialog) {
        // TODO : 다이얼로그 컴포넌트 추가후 구현
    }
}