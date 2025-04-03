package com.whatever.caramel.feature.copule.invite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.CaramelBalloonPopupWithImage
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.copule.invite.components.InviteBottomBar
import com.whatever.caramel.feature.copule.invite.components.InviteButton
import com.whatever.caramel.feature.copule.invite.mvi.CoupleInviteIntent
import com.whatever.caramel.feature.copule.invite.mvi.CoupleInviteState
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun CoupleInviteScreen(
    state: CoupleInviteState,
    onIntent: (CoupleInviteIntent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = CaramelTheme.color.background.primary,
        topBar = {
            CaramelTopBar(
                modifier = Modifier.statusBarsPadding(),
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable(
                            onClick = { onIntent(CoupleInviteIntent.ClickCloseButton) },
                            indication = null,
                            interactionSource = null
                        ),
                        painter = painterResource(resource = Resources.Icon.ic_cancel_24),
                        tint = CaramelTheme.color.icon.primary,
                        contentDescription = null
                    )
                }
            )
        },
        bottomBar = {
            InviteBottomBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = { onIntent(CoupleInviteIntent.ClickConnectCoupleButton) },
                        indication = null,
                        interactionSource = null
                    )
                    .padding(
                        horizontal = CaramelTheme.spacing.xl,
                        vertical = CaramelTheme.spacing.xxl
                    )
                    .navigationBarsPadding(),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(height = 8.dp))

            Text(
                text = "연인에게\n초대장을 보내고\n카라멜을 시작해 보세요!",
                style = CaramelTheme.typography.heading1,
                color = CaramelTheme.color.text.primary,
                textAlign = TextAlign.Center
            )

            CaramelBalloonPopupWithImage(
                modifier = Modifier.weight(weight = 1f),
                text = "상대방이 초대장을 수락해야\n시작할 수 있어요.",
                image = {
                    Box {
                        Box(
                            modifier = Modifier
                                .offset(y = 55.dp)
                                .fillMaxWidth()
                                .height(height = 125.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xFFFFE6C3),
                                            Color(0xFFFFE6C3).copy(alpha = 0f)
                                        )
                                    )
                                )
                        )

                        Image(
                            modifier = Modifier
                                .align(alignment = Alignment.TopCenter)
                                .size(
                                    width = 170.dp,
                                    height = 80.dp
                                ),
                            painter = painterResource(resource = Resources.Image.img_couple_on_ground),
                            contentDescription = null
                        )
                    }
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = CaramelTheme.spacing.xxl,
                        start = CaramelTheme.spacing.xl,
                        end = CaramelTheme.spacing.xl
                    ),
                horizontalArrangement = Arrangement.spacedBy(
                    space = CaramelTheme.spacing.m
                )
            ) {
                InviteButton(
                    text = "초대 코드\n복사하기",
                    icon = Resources.Icon.ic_link_24,
                    onClickButton = { onIntent(CoupleInviteIntent.ClickCopyInviteCodeButton) }
                )

                InviteButton(
                    text = "카라멜\n초대장 보내기",
                    icon = Resources.Icon.ic_send_24,
                    onClickButton = { onIntent(CoupleInviteIntent.ClickInviteButton) }
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = CaramelTheme.spacing.xl),
                thickness = 1.dp,
                color = CaramelTheme.color.divider.primary
            )
        }
    }
}