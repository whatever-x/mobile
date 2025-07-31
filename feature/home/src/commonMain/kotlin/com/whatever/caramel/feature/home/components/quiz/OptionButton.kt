package com.whatever.caramel.feature.home.components.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.home.mvi.BalanceGameCard
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun RowScope.OptionButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    gameResult: BalanceGameCard.GameResult,
    onClickOption: () -> Unit,
) {
    val backgroundColor =
        if (gameResult == BalanceGameCard.GameResult.IDLE) {
            CaramelTheme.color.fill.quinary
        } else {
            if (isSelected) {
                CaramelTheme.color.fill.brand
            } else {
                CaramelTheme.color.fill.disabledPrimary
            }
        }

    val textColor =
        if (gameResult == BalanceGameCard.GameResult.IDLE) {
            CaramelTheme.color.text.brand
        } else {
            if (isSelected) {
                CaramelTheme.color.text.inverse
            } else {
                CaramelTheme.color.text.disabledPrimary
            }
        }

    Box(
        modifier =
            modifier
                .weight(weight = 1f)
                .fillMaxHeight()
                .background(
                    color = backgroundColor,
                    shape = CaramelTheme.shape.m,
                ).clip(shape = CaramelTheme.shape.m)
                .clickable(
                    enabled = gameResult != BalanceGameCard.GameResult.WAITING,
                    onClick = onClickOption,
                ).padding(all = CaramelTheme.spacing.m),
        contentAlignment = Alignment.Center,
    ) {
        if (isSelected) {
            val optionString =
                buildAnnotatedString {
                    appendInlineContent(id = "check icon")
                    append(" ")
                    append(text)
                }
            val inlineContentMap =
                mapOf(
                    "check icon" to
                        InlineTextContent(
                            placeholder =
                                Placeholder(
                                    width = 16.sp,
                                    height = 16.sp,
                                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
                                ),
                            children = {
                                Icon(
                                    painter = painterResource(resource = Resources.Icon.ic_check_16),
                                    contentDescription = null,
                                    tint = CaramelTheme.color.icon.inverse,
                                )
                            },
                        ),
                )

            Text(
                text = optionString,
                inlineContent = inlineContentMap,
                style = CaramelTheme.typography.body3.bold,
                textAlign = TextAlign.Center,
                color = textColor,
            )
        } else {
            Text(
                text = text,
                style = CaramelTheme.typography.body3.bold,
                textAlign = TextAlign.Center,
                color = textColor,
            )
        }
    }
}
