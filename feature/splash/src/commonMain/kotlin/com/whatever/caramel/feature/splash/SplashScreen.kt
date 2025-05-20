package com.whatever.caramel.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import caramel.feature.splash.generated.resources.Res
import caramel.feature.splash.generated.resources.caramel
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.splash.mvi.SplashIntent
import com.whatever.caramel.feature.splash.mvi.SplashState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SplashScreen(
    state: SplashState,
    onIntent: (SplashIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = CaramelTheme.color.background.primary
            )
            .systemBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(
            space = CaramelTheme.spacing.l,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(resource = Resources.Image.img_splash),
            contentDescription = null
        )

        Text(
            text = stringResource(resource = Res.string.caramel),
            style = CaramelTheme.typography.display, // FIXME : 폰트 변경
            color = CaramelTheme.color.fill.primary // FIXME : 컬러 변경
        )
    }
}