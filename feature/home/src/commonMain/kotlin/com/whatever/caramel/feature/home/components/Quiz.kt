package com.whatever.caramel.feature.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import caramel.feature.home.generated.resources.Res
import caramel.feature.home.generated.resources.check_choice_button
import caramel.feature.home.generated.resources.check_our_choice
import caramel.feature.home.generated.resources.today_caramel
import caramel.feature.home.generated.resources.waiting_for_partner_answer
import caramel.feature.home.generated.resources.waku_we_will_choice_same
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.home.mvi.BalanceGameOption
import com.whatever.caramel.feature.home.mvi.HomeState
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

internal fun LazyListScope.Quiz(
    partnerNickname: String,
    myNickname: String,
    question: String,
    options: ImmutableList<BalanceGameOption>,
    myChoiceOption: BalanceGameOption,
    partnerChoiceOption: BalanceGameOption,
    balanceGameAnswerState: HomeState.BalanceGameAnswerState,
    balanceGameCardState: HomeState.BalanceGameCardState,
    onOptionClick: (BalanceGameOption) -> Unit,
    onClickResult: () -> Unit
) {
    item(key = "Quiz") {
        Box(
            modifier = Modifier
                .padding(horizontal = CaramelTheme.spacing.xl)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = CaramelTheme.color.background.tertiary,
                        shape = CaramelTheme.shape.l
                    )
                    .border(
                        width = 4.dp,
                        color = CaramelTheme.color.fill.quaternary,
                        shape = CaramelTheme.shape.l
                    )
                    .padding(top = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(resource = Res.string.today_caramel),
                    style = CaramelTheme.typography.body4.bold,
                    color = CaramelTheme.color.text.secondary
                )

                QuestionArea(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = CaramelTheme.spacing.xl)
                        .padding(
                            top = CaramelTheme.spacing.xl,
                            bottom = CaramelTheme.spacing.xxl
                        ),
                    question = question
                )

                when (balanceGameCardState) {
                    HomeState.BalanceGameCardState.IDLE -> {
                        ImageArea(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = CaramelTheme.spacing.l)
                                .padding(top = CaramelTheme.spacing.s),
                            balanceGameAnswerState = balanceGameAnswerState,
                            partnerNickname = partnerNickname
                        )

                        ButtonArea(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = CaramelTheme.spacing.l),
                            balanceGameOptions = options,
                            balanceGameAnswerState = balanceGameAnswerState,
                            myChoiceOption = myChoiceOption,
                            onClickOption = onOptionClick,
                            onClickResult = onClickResult
                        )
                    }
                    HomeState.BalanceGameCardState.CONFIRM -> {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = CaramelTheme.spacing.l),
                            thickness = 1.dp,
                            color = CaramelTheme.color.fill.quaternary,
                        )

                        BalanceGameResult(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = CaramelTheme.spacing.xl),
                            myNickname = myNickname,
                            partnerNickname = partnerNickname,
                            myChoiceOption = myChoiceOption,
                            partnerChoiceOption = partnerChoiceOption
                        )
                    }
                }
            }

            Image(
                modifier = Modifier
                    .align(alignment = Alignment.TopCenter)
                    .offset(y = (-15).dp),
                painter = painterResource(resource = Resources.Image.img_quiz_vs),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun QuestionArea(
    modifier: Modifier = Modifier,
    question: String
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = question,
            style = CaramelTheme.typography.heading2,
            color = CaramelTheme.color.text.primary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ImageArea(
    modifier: Modifier = Modifier,
    balanceGameAnswerState: HomeState.BalanceGameAnswerState,
    partnerNickname: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(resource = Resources.Image.img_quiz),
            contentDescription = null
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = CaramelTheme.color.fill.quaternary,
            thickness = 1.dp
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = CaramelTheme.spacing.m)
                .padding(top = CaramelTheme.spacing.l),
            contentAlignment = Alignment.Center
        ) {
            val text = when (balanceGameAnswerState) {
                HomeState.BalanceGameAnswerState.IDLE -> stringResource(Res.string.waku_we_will_choice_same)
                HomeState.BalanceGameAnswerState.WAITING -> stringResource(Res.string.waiting_for_partner_answer, partnerNickname)
                HomeState.BalanceGameAnswerState.CHECK_RESULT -> stringResource(Res.string.check_our_choice)
            }

            Text(
                text = text,
                style = CaramelTheme.typography.label1.bold,
                color = CaramelTheme.color.text.secondary
            )
        }
    }
}

@Composable
private fun ButtonArea(
    modifier: Modifier = Modifier,
    myChoiceOption: BalanceGameOption,
    balanceGameOptions: ImmutableList<BalanceGameOption>,
    balanceGameAnswerState: HomeState.BalanceGameAnswerState,
    onClickOption: (BalanceGameOption) -> Unit,
    onClickResult: () -> Unit
) {
    when (balanceGameAnswerState) {
        HomeState.BalanceGameAnswerState.WAITING,
        HomeState.BalanceGameAnswerState.IDLE -> {
            Row(
                modifier = modifier.height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(
                    space = CaramelTheme.spacing.s,
                )
            ) {
                balanceGameOptions.forEach { option ->
                    OptionButton(
                        modifier = Modifier,
                        text = option.name,
                        isSelected = myChoiceOption == option,
                        balanceGameAnswerState = balanceGameAnswerState,
                        onClick = { onClickOption(option) }
                    )
                }
            }
        }

        HomeState.BalanceGameAnswerState.CHECK_RESULT -> {
            Box(
                modifier = modifier
                    .background(
                        color = CaramelTheme.color.fill.brand,
                        shape = CaramelTheme.shape.m
                    )
                    .clip(shape = CaramelTheme.shape.m)
                    .clickable(
                        enabled = balanceGameAnswerState != HomeState.BalanceGameAnswerState.WAITING,
                        onClick = onClickResult
                    )
                    .padding(all = CaramelTheme.spacing.m),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(resource = Res.string.check_choice_button),
                    style = CaramelTheme.typography.body3.bold,
                    color = CaramelTheme.color.text.inverse
                )
            }
        }
    }
}

@Composable
private fun RowScope.OptionButton(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    balanceGameAnswerState: HomeState.BalanceGameAnswerState,
    onClick: () -> Unit
) {
    val backgroundColor = if (balanceGameAnswerState == HomeState.BalanceGameAnswerState.IDLE) {
        CaramelTheme.color.fill.quinary
    } else {
        if(isSelected) {
            CaramelTheme.color.fill.brand
        } else {
            CaramelTheme.color.fill.disabledPrimary
        }
    }

    val textColor = if (balanceGameAnswerState == HomeState.BalanceGameAnswerState.IDLE) {
        CaramelTheme.color.text.brand
    } else {
        if(isSelected) {
            CaramelTheme.color.text.inverse
        } else {
            CaramelTheme.color.text.disabledPrimary
        }
    }

    Box(
        modifier = modifier
            .weight(weight = 1f)
            .fillMaxHeight()
            .background(
                color = backgroundColor,
                shape = CaramelTheme.shape.m
            )
            .clip(shape = CaramelTheme.shape.m)
            .clickable(
                enabled = balanceGameAnswerState != HomeState.BalanceGameAnswerState.WAITING,
                onClick = onClick
            )
            .padding(all = CaramelTheme.spacing.m),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            val optionString = buildAnnotatedString {
                appendInlineContent(id = "check icon")
                append(" ")
                append(text)
            }
            val inlineContentMap = mapOf(
                "check icon" to InlineTextContent(
                    placeholder = Placeholder(
                        width = 16.sp,
                        height = 16.sp,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                    ),
                    children = {
                        Icon(
                            painter = painterResource(resource = Resources.Icon.ic_check_16),
                            contentDescription = null,
                            tint = CaramelTheme.color.icon.inverse
                        )
                    },
                )
            )

            Text(
                text = optionString,
                inlineContent = inlineContentMap,
                style = CaramelTheme.typography.body3.bold,
                textAlign = TextAlign.Center,
                color = textColor
            )
        } else {
            Text(
                text = text,
                style = CaramelTheme.typography.body3.bold,
                textAlign = TextAlign.Center,
                color = textColor
            )
        }
    }
}

@Composable
private fun BalanceGameResult(
    modifier: Modifier = Modifier,
    myNickname: String,
    partnerNickname: String,
    myChoiceOption: BalanceGameOption,
    partnerChoiceOption: BalanceGameOption,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = CaramelTheme.spacing.l,
            alignment = Alignment.CenterVertically
        ),
    ) {
        repeat(2) { index ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = CaramelTheme.spacing.m
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(
                        resource =
                            if (index == 0)  Resources.Image.img_quiz_my
                            else Resources.Image.img_quiz_partner
                    ),
                    contentDescription = null,
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(
                        space = CaramelTheme.spacing.xxs
                    )
                ) {
                    Text(
                        text = if (index == 0) myNickname else partnerNickname,
                        style = CaramelTheme.typography.body4.regular,
                        color = CaramelTheme.color.text.secondary
                    )

                    Text(
                        text = if (index == 0) myChoiceOption.name else partnerChoiceOption.name,
                        style = CaramelTheme.typography.body1.bold,
                        color = CaramelTheme.color.text.primary
                    )
                }
            }
        }
    }
}