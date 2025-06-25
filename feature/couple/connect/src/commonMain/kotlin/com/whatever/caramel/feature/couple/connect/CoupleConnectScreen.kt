package com.whatever.caramel.feature.couple.connect

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.couple.connect.components.CoupleConnectBottomBar
import com.whatever.caramel.feature.couple.connect.components.CoupleConnectContents
import com.whatever.caramel.feature.couple.connect.mvi.CoupleConnectIntent
import com.whatever.caramel.feature.couple.connect.mvi.CoupleConnectState
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun CoupleConnectScreen(
    state: CoupleConnectState,
    onIntent: (CoupleConnectIntent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = CaramelTheme.color.background.primary,
        bottomBar = {
            CoupleConnectBottomBar(
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding(),
                buttonEnabled = state.isButtonEnabled,
                buttonText = "연결하기",
                onClickButton = { onIntent(CoupleConnectIntent.ClickConnectButton) }
            )
        },
        topBar = {
            CaramelTopBar(
                modifier = Modifier.systemBarsPadding(),
                leadingContent = {
                    Icon(
                        modifier = Modifier
                            .clickable(
                                onClick = { onIntent(CoupleConnectIntent.ClickBackButton) },
                                interactionSource = null,
                                indication = null
                            ),
                        painter = painterResource(resource = Resources.Icon.ic_arrow_left_24),
                        contentDescription = null,
                        tint = CaramelTheme.color.icon.primary
                    )
                }
            )
        }
    ) { innerPadding ->
        CoupleConnectContents(
            modifier = Modifier.padding(paddingValues = innerPadding),
            code = state.invitationCode,
            onCodeChange = { code -> onIntent(CoupleConnectIntent.ChangeInvitationCode(code)) }
        )
    }
}