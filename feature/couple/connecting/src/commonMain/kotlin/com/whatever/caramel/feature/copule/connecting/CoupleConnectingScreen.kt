package com.whatever.caramel.feature.copule.connecting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import caramel.feature.couple.connecting.generated.resources.Res
import caramel.feature.couple.connecting.generated.resources.go_to_home
import caramel.feature.couple.connecting.generated.resources.welcome_to_caramel
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.copule.connecting.mvi.CoupleConnectingIntent
import com.whatever.caramel.feature.copule.connecting.mvi.CoupleConnectingState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CoupleConnectingScreen(
    state: CoupleConnectingState,
    onIntent: (CoupleConnectingIntent) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = CaramelTheme.color.background.primary),
        verticalArrangement =
            Arrangement.spacedBy(
                space = CaramelTheme.spacing.xl,
                alignment = Alignment.CenterVertically,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(resource = Resources.Image.img_matchingsuccess),
            contentDescription = null,
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement =
                Arrangement.spacedBy(
                    space = CaramelTheme.spacing.s,
                ),
        ) {
            Text(
                text = stringResource(resource = Res.string.welcome_to_caramel),
                style = CaramelTheme.typography.body3.regular,
                color = CaramelTheme.color.text.primary,
            )

            Text(
                text = stringResource(resource = Res.string.go_to_home),
                textAlign = TextAlign.Center,
                style = CaramelTheme.typography.heading2,
                color = CaramelTheme.color.text.primary,
            )
        }
    }
}
