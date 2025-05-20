package com.whatever.caramel.feature.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.feature.login.components.OnboardingPager
import com.whatever.caramel.feature.login.components.OnboardingStep
import com.whatever.caramel.feature.login.mvi.LoginIntent
import com.whatever.caramel.feature.login.mvi.LoginState
import com.whatever.caramel.feature.login.util.Platform

@Composable
internal fun LoginScreen(
    state: LoginState,
    onIntent: (LoginIntent) -> Unit,
    onLaunch: (SocialLoginType) -> Unit,
) {
    val pagerState = rememberPagerState { OnboardingStep.entries.size }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = CaramelTheme.color.background.primary
            )
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OnboardingPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1f),
            pagerState = pagerState,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = CaramelTheme.spacing.xl),
            verticalArrangement = Arrangement.spacedBy(
                space = CaramelTheme.spacing.s
            )
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { onLaunch(SocialLoginType.KAKAO) }
            ) {
                Text(
                    text = "카카오 로그인",
                    fontSize = 18.sp
                )
            }

            if (Platform.isIos) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = { onLaunch(SocialLoginType.APPLE) }
                ) {
                    Text(
                        text = "애플 로그인",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}