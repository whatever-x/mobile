package com.whatever.caramel.feature.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import com.whatever.caramel.feature.login.components.OnboardingPager
import com.whatever.caramel.feature.login.components.OnboardingStep
import com.whatever.caramel.feature.login.components.SocialLoginButton
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
                .padding(horizontal = CaramelTheme.spacing.xl)
                .padding(bottom = CaramelTheme.spacing.xl),
            verticalArrangement = Arrangement.spacedBy(
                space = CaramelTheme.spacing.s
            ),
        ) {
            SocialLoginButton(
                socialLoginType = SocialLoginType.KAKAO,
                onLaunch = { onLaunch(SocialLoginType.KAKAO) }
            )

            if (Platform.isIos) {
                SocialLoginButton(
                    socialLoginType = SocialLoginType.APPLE,
                    onLaunch = { onLaunch(SocialLoginType.APPLE) }
                )
            }
        }
    }
}