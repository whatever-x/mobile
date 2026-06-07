package com.whatever.caramel.feature.balancegame.share

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import caramel.feature.balancegame.generated.resources.Res
import caramel.feature.balancegame.generated.resources.balance_game_share_title
import com.whatever.caramel.core.designsystem.components.CaramelTopBar
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.balancegame.share.mvi.BalanceGameShareIntent
import com.whatever.caramel.feature.balancegame.share.mvi.BalanceGameShareState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun BalanceGameShareScreen(
    state: BalanceGameShareState,
    onIntent: (BalanceGameShareIntent) -> Unit,
) {
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
    }
}
