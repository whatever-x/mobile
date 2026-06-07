package com.whatever.caramel.feature.balancegame.share

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import caramel.feature.balancegame.generated.resources.Res
import caramel.feature.balancegame.generated.resources.balance_game_share_title
import com.whatever.caramel.core.designsystem.components.CaramelButton
import com.whatever.caramel.core.designsystem.components.CaramelButtonSize
import com.whatever.caramel.core.designsystem.components.CaramelButtonType
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.balancegame.share.capture.capturable
import com.whatever.caramel.feature.balancegame.share.capture.rememberCaptureController
import com.whatever.caramel.feature.balancegame.share.component.MockShareCard
import com.whatever.caramel.feature.balancegame.share.mvi.BalanceGameShareIntent
import com.whatever.caramel.feature.balancegame.share.mvi.BalanceGameShareState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun BalanceGameShareScreen(
    state: BalanceGameShareState,
    onIntent: (BalanceGameShareIntent) -> Unit,
) {
    val captureController = rememberCaptureController()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = CaramelTheme.color.background.primary),
    ) {
        CaramelTopBar(
            modifier = Modifier.statusBarsPadding(),
            centerContents = {
                Text(
                    text = stringResource(resource = Res.string.balance_game_share_title),
                    style = CaramelTheme.typography.heading3,
                    color = CaramelTheme.color.text.primary,
                )
            },
            leadingContent = {
                Icon(
                    modifier =
                        Modifier.clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = { onIntent(BalanceGameShareIntent.ClickBackButton) },
                        ),
                    painter = painterResource(resource = Resources.Icon.ic_arrow_left_24),
                    tint = CaramelTheme.color.icon.primary,
                    contentDescription = null,
                )
            },
        )

        Box(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(CaramelTheme.spacing.xl),
            contentAlignment = Alignment.Center,
        ) {
            MockShareCard(modifier = Modifier.capturable(captureController))
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(
                        horizontal = CaramelTheme.spacing.xl,
                        vertical = CaramelTheme.spacing.l,
                    ),
            horizontalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.s),
        ) {
            val buttonType =
                if (state.exportInProgress) CaramelButtonType.Disabled else CaramelButtonType.Enabled2
            CaramelButton(
                modifier = Modifier.weight(1f),
                buttonType = buttonType,
                buttonSize = CaramelButtonSize.Large,
                text = "저장하기",
                onClick = {
                    coroutineScope.launch {
                        val image = captureController.capture()
                        onIntent(BalanceGameShareIntent.ClickSaveImage(image))
                    }
                },
            )
            CaramelButton(
                modifier = Modifier.weight(1f),
                buttonType =
                    if (state.exportInProgress) CaramelButtonType.Disabled else CaramelButtonType.Enabled1,
                buttonSize = CaramelButtonSize.Large,
                text = "공유하기",
                onClick = {
                    coroutineScope.launch {
                        val image = captureController.capture()
                        onIntent(BalanceGameShareIntent.ClickShareImage(image))
                    }
                },
            )
        }
    }
}
