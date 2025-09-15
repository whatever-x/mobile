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
import caramel.feature.splash.generated.resources.Res
import caramel.feature.splash.generated.resources.force_update_button
import caramel.feature.splash.generated.resources.force_update_message
import caramel.feature.splash.generated.resources.force_update_title
import com.whatever.caramel.core.designsystem.components.CaramelDialog
import com.whatever.caramel.core.designsystem.components.DefaultCaramelDialogLayout
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.splash.mvi.SplashIntent
import com.whatever.caramel.feature.splash.mvi.SplashState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SplashScreen(
    state: SplashState,
    onIntent: (SplashIntent) -> Unit,
) {
    CaramelDialog(
        show = state.isForceUpdate,
        title = stringResource(resource = Res.string.force_update_title),
        message = stringResource(resource = Res.string.force_update_message),
        mainButtonText = stringResource(resource = Res.string.force_update_button),
        onDismissRequest = { },
        onMainButtonClick = { onIntent(SplashIntent.ClickUpdate) },
    ) {
        DefaultCaramelDialogLayout()
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    color = CaramelTheme.color.background.primary,
                ).systemBarsPadding(),
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
            tint = CaramelTheme.color.fill.primary,
        )
    }
}
