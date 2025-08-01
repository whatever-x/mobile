@file:OptIn(ExperimentalMaterial3Api::class)

package com.whatever.caramel.feature.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.CaramelDialog
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.components.DefaultCaramelDialogLayout
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.setting.component.SettingEditProfileBottomSheet
import com.whatever.caramel.feature.setting.component.SettingListButton
import com.whatever.caramel.feature.setting.component.SettingListText
import com.whatever.caramel.feature.setting.component.SettingUserProfile
import com.whatever.caramel.feature.setting.mvi.SettingIntent
import com.whatever.caramel.feature.setting.mvi.SettingState
import com.whatever.caramel.feature.setting.util.Platform
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun SettingScreen(
    state: SettingState,
    onIntent: (SettingIntent) -> Unit,
) {
    val scrollState = rememberScrollState()

    SettingDialogHost(
        isShowUserCancelledDialog = state.isShowUserCancelledDialog,
        isShowLogoutDialog = state.isShowLogoutDialog,
        isShowEditProfileBottomSheet = state.isShowEditProfileBottomSheet,
        toggleEditProfileDialog = { onIntent(SettingIntent.ToggleEditProfile) },
        onClickEditNickname = { onIntent(SettingIntent.ClickEditNicknameButton) },
        onClickEditBrithDay = { onIntent(SettingIntent.ClickEditBirthDayButton) },
        toggleLogoutDialog = { onIntent(SettingIntent.ToggleLogout) },
        toggleUserCancelledDialog = { onIntent(SettingIntent.ToggleUserCancelledButton) },
        onClickLogout = { onIntent(SettingIntent.ClickLogoutConfirmButton) },
        onClickUserCancelled = { onIntent(SettingIntent.ClickUserCancelledConfirmButton) },
    )

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = CaramelTheme.color.background.primary),
    ) {
        CaramelTopBar(
            modifier =
                Modifier
                    .statusBarsPadding(),
            centerContents = {
                Text(
                    text = "설정",
                    style = CaramelTheme.typography.heading3,
                    color = CaramelTheme.color.text.primary,
                )
            },
            leadingContent = {
                Icon(
                    modifier =
                        Modifier
                            .clickable(
                                indication = null,
                                interactionSource = null,
                                onClick = { onIntent(SettingIntent.ClickSettingBackButton) },
                            ),
                    painter = painterResource(Resources.Icon.ic_arrow_left_24),
                    contentDescription = null,
                )
            },
        )

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
        ) {
            Column(modifier = Modifier.padding(all = CaramelTheme.spacing.xl)) {
                Text(
                    text = "우리의 시작",
                    style = CaramelTheme.typography.heading1,
                    color = CaramelTheme.color.text.primary,
                )
                Row(
                    modifier =
                        Modifier
                            .clickable(
                                onClick = { onIntent(SettingIntent.ClickEditCountDownButton) },
                                interactionSource = null,
                                indication = null,
                            ),
                ) {
                    Text(
                        text =
                            state.startDate.ifEmpty {
                                "언제부터 사귀기 시작했나요?"
                            },
                        style = CaramelTheme.typography.body2.regular,
                        color = CaramelTheme.color.text.secondary,
                    )
                    Icon(
                        modifier =
                            Modifier.padding(
                                all = CaramelTheme.spacing.xs,
                            ),
                        painter = painterResource(Resources.Icon.ic_edit_14),
                        tint = CaramelTheme.color.icon.tertiary,
                        contentDescription = null,
                    )
                }
                Spacer(modifier = Modifier.padding(bottom = 20.dp))
                SettingUserProfile(
                    isLoading = state.isLoading,
                    gender = state.myInfo.gender,
                    nickname = state.myInfo.nickname,
                    birthDay = state.myInfo.birthday,
                    isEditable = true,
                    onClickEditProfile = { onIntent(SettingIntent.ToggleEditProfile) },
                )
                Spacer(modifier = Modifier.padding(bottom = CaramelTheme.spacing.m))
                SettingUserProfile(
                    isLoading = state.isLoading,
                    gender = state.partnerInfo.gender,
                    nickname = state.partnerInfo.nickname,
                    birthDay = state.partnerInfo.birthday,
                    isEditable = false,
                )
            }
            HorizontalDivider(
                modifier =
                    Modifier.padding(
                        top = 10.dp,
                        bottom = 30.dp,
                    ),
                color = CaramelTheme.color.divider.primary,
            )

            Column(modifier = Modifier.padding(horizontal = CaramelTheme.spacing.xl)) {
                Text(
                    modifier = Modifier,
                    text = "서비스 소개",
                    style = CaramelTheme.typography.heading3,
                    color = CaramelTheme.color.text.primary,
                )
                Spacer(
                    modifier =
                        Modifier.padding(
                            bottom = CaramelTheme.spacing.s,
                        ),
                )
                SettingListButton(
                    mainText = "알림",
                    isChecked = state.isNotificationEnabled,
                    onClickTailButton = { onIntent(SettingIntent.ClickNotificationToggleButton) },
                )
                SettingListText(
                    mainText = "앱 버전 v.${Platform.versionName}",
                    mainTextColor = CaramelTheme.color.text.primary,
                    onClickTailText = { onIntent(SettingIntent.ClickAppUpdateButton) },
                )
                SettingListText(
                    mainText = "서비스 이용약관",
                    mainTextColor = CaramelTheme.color.text.primary,
                    onClickListItem = { onIntent(SettingIntent.ClickTermsOfServiceButtons) },
                )
                SettingListText(
                    mainText = "개인정보 처리방침",
                    mainTextColor = CaramelTheme.color.text.primary,
                    onClickListItem = { onIntent(SettingIntent.ClickPrivacyPolicyButton) },
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .navigationBarsPadding()
                            .align(Alignment.BottomStart),
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
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingDialogHost(
    isShowEditProfileBottomSheet: Boolean,
    isShowLogoutDialog: Boolean,
    isShowUserCancelledDialog: Boolean,
    toggleEditProfileDialog: () -> Unit,
    toggleLogoutDialog: () -> Unit,
    toggleUserCancelledDialog: () -> Unit,
    onClickEditNickname: () -> Unit,
    onClickEditBrithDay: () -> Unit,
    onClickLogout: () -> Unit,
    onClickUserCancelled: () -> Unit,
) {
    if (isShowEditProfileBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = toggleEditProfileDialog,
            contentWindowInsets = { WindowInsets(0, 0, 0, 0) },
            dragHandle = null,
            shape =
                RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp,
                ),
            scrimColor = CaramelTheme.color.alpha.primary,
        ) {
            SettingEditProfileBottomSheet(
                modifier =
                    Modifier
                        .background(color = CaramelTheme.color.background.tertiary)
                        .navigationBarsPadding(),
                navigateToProfileEditNickName = onClickEditNickname,
                navigateToProfileEditBrithDay = onClickEditBrithDay,
            )
        }
    }

    CaramelDialog(
        show = isShowLogoutDialog,
        title = "로그아웃하시겠어요?",
        mainButtonText = "로그아웃",
        subButtonText = "유지하기",
        onMainButtonClick = {
            toggleLogoutDialog()
            onClickLogout()
        },
        onSubButtonClick = toggleLogoutDialog,
        onDismissRequest = toggleLogoutDialog,
    ) {
        DefaultCaramelDialogLayout()
    }

    CaramelDialog(
        show = isShowUserCancelledDialog,
        title = "탈퇴하시겠어요?",
        message = "탈퇴하면 이 공간에 다시 들어올 수 없어요.",
        mainButtonText = "탈퇴하기",
        subButtonText = "유지하기",
        onMainButtonClick = {
            toggleUserCancelledDialog()
            onClickUserCancelled()
        },
        onSubButtonClick = toggleUserCancelledDialog,
        onDismissRequest = toggleUserCancelledDialog,
    ) {
        DefaultCaramelDialogLayout()
    }
}
