package com.whatever.caramel.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.splash.mvi.SplashIntent
import com.whatever.caramel.feature.splash.mvi.SplashState
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun SplashScreen(
    state: SplashState,
    onIntent: (SplashIntent) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    color = CaramelTheme.color.background.primary,
                )
                .systemBarsPadding(),
        verticalArrangement =
            Arrangement.spacedBy(
                space = CaramelTheme.spacing.l,
                alignment = Alignment.CenterVertically,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(resource = Resources.Image.img_splash),
            contentDescription = null,
        )

        // @ham2174 FIXME : 추후 폰트 정의될 시 텍스트로 변경 예정
        Icon(
            painter = painterResource(resource = Resources.Image.img_type_logo),
            contentDescription = null,
            tint = Color.Unspecified,
        )
    }
}
