package com.whatever.caramel.feature.balancegame.history.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import caramel.feature.balancegame.generated.resources.Res
import caramel.feature.balancegame.generated.resources.balance_game_history_share_result
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.feature.balancegame.history.model.BalanceGameHistoryUiModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun BalanceGameHistoryCard(
    item: BalanceGameHistoryUiModel,
    expanded: Boolean,
    myNickname: String,
    myGender: Gender,
    partnerNickname: String,
    partnerGender: Gender,
    onClickCard: () -> Unit,
    onClickShare: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    color = CaramelTheme.color.background.tertiary,
                    shape = CaramelTheme.shape.l,
                ).border(
                    width = 1.dp,
                    color = CaramelTheme.color.fill.quaternary,
                    shape = CaramelTheme.shape.l,
                ),
    ) {
        Header(
            item = item,
            expanded = expanded,
            onClickCard = onClickCard,
        )

        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut(),
        ) {
            ExpandedResult(
                item = item,
                myNickname = myNickname,
                myGender = myGender,
                partnerNickname = partnerNickname,
                partnerGender = partnerGender,
                onClickShare = onClickShare,
            )
        }
    }
}

@Composable
private fun Header(
    item: BalanceGameHistoryUiModel,
    expanded: Boolean,
    onClickCard: () -> Unit,
) {
    val rotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "chevronRotation",
    )
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = onClickCard,
                )
                .padding(
                    horizontal = CaramelTheme.spacing.l,
                    vertical = 14.dp,
                ),
        horizontalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.m),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.xs),
        ) {
            Text(
                text = item.question,
                style = CaramelTheme.typography.heading3,
                color = CaramelTheme.color.text.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = item.dateText,
                style = CaramelTheme.typography.body3.regular,
                color = CaramelTheme.color.text.secondary,
            )
        }
        Icon(
            modifier = Modifier.size(16.dp).rotate(rotation),
            painter = painterResource(resource = Resources.Icon.ic_arrow_down_16),
            tint = CaramelTheme.color.icon.primary,
            contentDescription = null,
        )
    }
}

@Composable
private fun ExpandedResult(
    item: BalanceGameHistoryUiModel,
    myNickname: String,
    myGender: Gender,
    partnerNickname: String,
    partnerGender: Gender,
    onClickShare: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    start = CaramelTheme.spacing.l,
                    end = CaramelTheme.spacing.l,
                    bottom = CaramelTheme.spacing.l,
                ),
        verticalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.l),
    ) {
        HorizontalDivider(color = CaramelTheme.color.divider.primary)

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = item.question,
            style = CaramelTheme.typography.heading2,
            color = CaramelTheme.color.text.primary,
            textAlign = TextAlign.Center,
        )

        ProfileRow(
            nickname = myNickname,
            gender = myGender,
            choiceText = item.myChoiceText,
        )
        ProfileRow(
            nickname = partnerNickname,
            gender = partnerGender,
            choiceText = item.partnerChoiceText,
        )

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .background(
                        color = CaramelTheme.color.fill.quinary,
                        shape = CaramelTheme.shape.l,
                    ).clickable(
                        indication = null,
                        interactionSource = null,
                        onClick = onClickShare,
                    ),
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(resource = Resources.Icon.ic_share_16),
                tint = CaramelTheme.color.icon.primary,
                contentDescription = null,
            )
            Text(
                text = stringResource(resource = Res.string.balance_game_history_share_result),
                style = CaramelTheme.typography.body4.bold,
                color = CaramelTheme.color.text.primary,
            )
        }
    }
}

@Composable
private fun ProfileRow(
    nickname: String,
    gender: Gender,
    choiceText: String,
) {
    val genderImage =
        when (gender) {
            Gender.MALE -> Resources.Image.img_quiz_man
            Gender.FEMALE -> Resources.Image.img_quiz_woman
            else -> Resources.Image.img_quiz_man
        }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.m),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(resource = genderImage),
            contentDescription = null,
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(CaramelTheme.spacing.xxs),
        ) {
            Text(
                text = nickname,
                style = CaramelTheme.typography.body4.regular,
                color = CaramelTheme.color.text.secondary,
            )
            Text(
                text = choiceText,
                style = CaramelTheme.typography.body1.bold,
                color = CaramelTheme.color.text.primary,
            )
        }
    }
}
