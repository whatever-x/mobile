package com.whatever.caramel.feature.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.setting.component.SettingListText
import com.whatever.caramel.feature.setting.component.SettingProfileChangeBottomSheet
import com.whatever.caramel.feature.setting.component.SettingUserProfile
import com.whatever.caramel.feature.setting.component.SettingUserProfileSkeleton
import com.whatever.caramel.feature.setting.mvi.SettingIntent
import com.whatever.caramel.feature.setting.mvi.SettingState
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingScreen(
    state: SettingState,
    onIntent: (SettingIntent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    if (state.isShowProfileChangeBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {},
            sheetState = sheetState,
            dragHandle = null,
            shape = RoundedCornerShape(
                topStart = 24.dp,
                topEnd = 24.dp
            ),
            scrimColor = CaramelTheme.color.alpha.primary
        ) {
            SettingProfileChangeBottomSheet(
                navigateToProfileEditNickName = { onIntent(SettingIntent.ClickEditNicknameButton) },
                navigateToProfileEditBrithDay = { onIntent(SettingIntent.ClickEditBirthdayButton) }
            )
        }
    }
    Box(
        modifier = Modifier
            .background(color = CaramelTheme.color.background.primary)
            .fillMaxSize()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(
                        start = 16.dp
                    )
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .padding(
                            vertical = 14.dp
                        ),
                    painter = painterResource(Resources.Icon.ic_arrow_left_24),
                    contentDescription = null
                )
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "설정",
                        style = CaramelTheme.typography.heading2,
                        color = CaramelTheme.color.text.primary
                    )
                }
            }
            Column(
                modifier = Modifier.padding(all = CaramelTheme.spacing.xl)
            ) {
                Text(
                    text = "우리의 시작",
                    style = CaramelTheme.typography.heading1,
                    color = CaramelTheme.color.text.primary
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = CaramelTheme.spacing.xxs
                        )
                        .clickable(
                            onClick = { onIntent(SettingIntent.ClickEditBirthdayButton) },
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
            }
            // userProfile
            if (state.isLoading) {
                SettingUserProfileSkeleton()
                Spacer(modifier = Modifier.padding(bottom = CaramelTheme.spacing.m))
                SettingUserProfileSkeleton()
            } else {
                SettingUserProfile(
                    gender = state.myInfo.gender,
                    nickname = state.myInfo.nickname,
                    birthDay = state.myInfo.birthDate,
                    isEditable = true
                )
                Spacer(modifier = Modifier.padding(bottom = CaramelTheme.spacing.m))
                SettingUserProfile(
                    gender = state.partnerInfo.gender,
                    nickname = state.partnerInfo.nickname,
                    birthDay = state.partnerInfo.birthDate,
                    isEditable = false
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 30.dp),
                color = CaramelTheme.color.divider.primary
            )

            // 서비스 소개
            Text(
                modifier = Modifier
                    .padding(start = CaramelTheme.spacing.xl),
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
                onClick = { },
                onTailClick = { /* handle update click */ }
            )
            SettingListText(
                mainText = "서비스 이용약관",
                mainTextColor = CaramelTheme.color.text.primary,
                onClick = { onIntent(SettingIntent.ClickTermsOfServiceButtons) }
            )
            SettingListText(
                mainText = "개인정보 처리방침",
                mainTextColor = CaramelTheme.color.text.primary,
                onClick = { onIntent(SettingIntent.ClickPrivacyPolicyButton) },
            )
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier.align(
                        Alignment.BottomStart
                    )
                ) {
                    SettingListText(
                        mainText = "로그아웃",
                        mainTextColor = CaramelTheme.color.text.tertiary,
                        onClick = { onIntent(SettingIntent.ClickLogoutButton) },
                    )

                    SettingListText(
                        mainText = "탈퇴하기",
                        mainTextColor = CaramelTheme.color.text.tertiary,
                        onClick = { onIntent(SettingIntent.ClickCancelButton) },
                    )
                }
            }
        }
    }
}