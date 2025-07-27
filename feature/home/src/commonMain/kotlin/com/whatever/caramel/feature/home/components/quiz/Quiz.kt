package com.whatever.caramel.feature.home.components.quiz

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import caramel.feature.home.generated.resources.Res
import caramel.feature.home.generated.resources.check_choice_button
import caramel.feature.home.generated.resources.check_our_choice
import caramel.feature.home.generated.resources.today_caramel
import caramel.feature.home.generated.resources.waiting_for_partner_answer
import caramel.feature.home.generated.resources.waku_we_will_choice_same
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.feature.home.mvi.BalanceGameCard
import com.whatever.caramel.feature.home.mvi.BalanceGameOptionItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

internal fun LazyListScope.quiz(
    balanceGameCard: BalanceGameCard,
    isBalanceGameCardRotated: Boolean,
    myNickname: String,
    myGender: Gender,
    partnerNickname: String,
    partnerGender: Gender,
    onOptionClick: (BalanceGameOptionItem) -> Unit,
    onRotateCard: () -> Unit,
) {
    item(key = "Quiz") {
        val rotation = remember { Animatable(0f) }
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            snapshotFlow { rotation.value >= 90f }
                .distinctUntilChanged()
                .filter { it }
                .collect { onRotateCard() }
        }

        Box(
            modifier =
                Modifier
                    .graphicsLayer {
                        this.rotationY = rotation.value
                        cameraDistance = 8 * density
                    }.padding(horizontal = CaramelTheme.spacing.xl),
        ) {
            val contentRotationY = if (rotation.value < 90f) 0f else 180f

            Column(
                modifier =
                    Modifier
                        .graphicsLayer { rotationY = contentRotationY }
                        .fillMaxWidth()
                        .background(
                            color = CaramelTheme.color.background.tertiary,
                            shape = CaramelTheme.shape.l,
                        ).border(
                            width = 4.dp,
                            color = CaramelTheme.color.fill.quaternary,
                            shape = CaramelTheme.shape.l,
                        ).padding(top = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(resource = Res.string.today_caramel),
                    style = CaramelTheme.typography.body4.bold,
                    color = CaramelTheme.color.text.secondary,
                )

                QuestionArea(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = CaramelTheme.spacing.l)
                            .padding(
                                top = CaramelTheme.spacing.l,
                                bottom = CaramelTheme.spacing.xxl,
                            ),
                    question = balanceGameCard.question,
                )

                if (!isBalanceGameCardRotated) {
                    ImageArea(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = CaramelTheme.spacing.l)
                                .padding(top = CaramelTheme.spacing.s),
                        gameResult = balanceGameCard.gameResult,
                        partnerNickname = partnerNickname,
                    )

                    ButtonArea(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(all = CaramelTheme.spacing.l),
                        balanceGameOptionStates = balanceGameCard.options,
                        gameResult = balanceGameCard.gameResult,
                        myChoiceOption = balanceGameCard.myOption,
                        onClickOption = onOptionClick,
                        onClickResult = {
                            scope.launch {
                                rotation.animateTo(
                                    targetValue = rotation.value + 180f,
                                    animationSpec = tween(durationMillis = 1000),
                                )
                            }
                        },
                    )
                } else {
                    HorizontalDivider(
                        modifier =
                            Modifier
                                .padding(horizontal = CaramelTheme.spacing.l),
                        thickness = 1.dp,
                        color = CaramelTheme.color.fill.quaternary,
                    )

                    BalanceGameResult(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(all = CaramelTheme.spacing.xl),
                        myNickname = myNickname,
                        myGender = myGender,
                        partnerNickname = partnerNickname,
                        partnerGender = partnerGender,
                        myChoiceOption = balanceGameCard.myOption!!,
                        partnerChoiceOption = balanceGameCard.partnerOption!!,
                    )
                }
            }

            Image(
                modifier =
                    Modifier
                        .graphicsLayer { rotationY = contentRotationY }
                        .align(alignment = Alignment.TopCenter)
                        .offset(y = (-19).dp),
                painter = painterResource(resource = Resources.Image.img_quiz_vs),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun QuestionArea(
    modifier: Modifier = Modifier,
    question: String,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = question,
            style = CaramelTheme.typography.heading2,
            color = CaramelTheme.color.text.primary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ImageArea(
    modifier: Modifier = Modifier,
    gameResult: BalanceGameCard.GameResult,
    partnerNickname: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(resource = Resources.Image.img_quiz),
            contentDescription = null,
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = CaramelTheme.color.fill.quaternary,
            thickness = 1.dp,
        )

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = CaramelTheme.spacing.m)
                    .padding(top = CaramelTheme.spacing.l),
            contentAlignment = Alignment.Center,
        ) {
            val description =
                when (gameResult) {
                    BalanceGameCard.GameResult.IDLE -> stringResource(Res.string.waku_we_will_choice_same)
                    BalanceGameCard.GameResult.WAITING -> stringResource(Res.string.waiting_for_partner_answer, partnerNickname)
                    BalanceGameCard.GameResult.CHECK_RESULT -> stringResource(Res.string.check_our_choice)
                }

            Text(
                text = description,
                style = CaramelTheme.typography.label1.bold,
                color = CaramelTheme.color.text.secondary,
            )
        }
    }
}

@Composable
private fun ButtonArea(
    modifier: Modifier = Modifier,
    balanceGameOptionStates: ImmutableList<BalanceGameOptionItem>,
    myChoiceOption: BalanceGameOptionItem?,
    gameResult: BalanceGameCard.GameResult,
    onClickOption: (BalanceGameOptionItem) -> Unit,
    onClickResult: () -> Unit,
) {
    when (gameResult) {
        BalanceGameCard.GameResult.WAITING,
        BalanceGameCard.GameResult.IDLE,
        -> {
            Row(
                modifier = modifier.height(IntrinsicSize.Min),
                horizontalArrangement =
                    Arrangement.spacedBy(
                        space = CaramelTheme.spacing.s,
                    ),
            ) {
                balanceGameOptionStates.forEach { option ->
                    OptionButton(
                        text = option.name,
                        isSelected = myChoiceOption?.id == option.id,
                        gameResult = gameResult,
                        onClickOption = { onClickOption(option) },
                    )
                }
            }
        }

        BalanceGameCard.GameResult.CHECK_RESULT -> {
            Box(
                modifier =
                    modifier
                        .background(
                            color = CaramelTheme.color.fill.brand,
                            shape = CaramelTheme.shape.m,
                        ).clip(shape = CaramelTheme.shape.m)
                        .clickable(
                            enabled = gameResult != BalanceGameCard.GameResult.WAITING,
                            onClick = onClickResult,
                        ).padding(all = CaramelTheme.spacing.m),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(resource = Res.string.check_choice_button),
                    style = CaramelTheme.typography.body3.bold,
                    color = CaramelTheme.color.text.inverse,
                )
            }
        }
    }
}
