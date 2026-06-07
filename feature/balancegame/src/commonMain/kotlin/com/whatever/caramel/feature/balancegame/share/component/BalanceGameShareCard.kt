package com.whatever.caramel.feature.balancegame.share.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import caramel.feature.balancegame.generated.resources.Res
import caramel.feature.balancegame.generated.resources.img_balance_share_bg
import caramel.feature.balancegame.generated.resources.img_balance_share_female
import caramel.feature.balancegame.generated.resources.img_balance_share_male
import caramel.feature.balancegame.generated.resources.img_balance_share_message_heart
import caramel.feature.balancegame.generated.resources.img_balance_share_vs
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.user.Gender
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

private val CARD_WIDTH = 360.dp
private val CARD_HEIGHT = 640.dp
private const val CARD_RATIO = 360f / 640f

private val QUESTION_BADGE_COLOR = Color(0xFFFFB75F)
private val QUESTION_UNDERLINE_COLOR = Color(0xFFFFE6C3)
private val CHOICE_ME_BACKGROUND_COLOR = Color(0xFFFFEFEF)
private val CHOICE_ME_BORDER_COLOR = Color(0xFFFFB2B2)
private val CHOICE_PARTNER_BACKGROUND_COLOR = Color(0xFFF1FFF1)
private val CHOICE_PARTNER_BORDER_COLOR = Color(0xFFAFEDA7)
private val MESSAGE_BOX_BORDER_COLOR = Color(0xFFFFE6C3).copy(alpha = 0.65f)

@Composable
internal fun BalanceGameShareCard(
    question: String,
    myChoice: String,
    partnerChoice: String,
    myGender: Gender,
    partnerGender: Gender,
    isSameChoice: Boolean,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = modifier.aspectRatio(ratio = CARD_RATIO, matchHeightConstraintsFirst = true),
        contentAlignment = Alignment.Center,
    ) {
        val scale = (maxWidth / CARD_WIDTH).coerceAtMost(maxHeight / CARD_HEIGHT)
        Box(
            modifier =
                Modifier
                    .requiredSize(width = CARD_WIDTH, height = CARD_HEIGHT)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    },
        ) {
            CardContent(
                question = question,
                myChoice = myChoice,
                partnerChoice = partnerChoice,
                myGender = myGender,
                partnerGender = partnerGender,
                isSameChoice = isSameChoice,
            )
        }
    }
}

@Composable
private fun CardContent(
    question: String,
    myChoice: String,
    partnerChoice: String,
    myGender: Gender,
    partnerGender: Gender,
    isSameChoice: Boolean,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(resource = Res.drawable.img_balance_share_bg),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )

        Column(
            modifier = Modifier.align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = CaramelTheme.spacing.s),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(space = CaramelTheme.spacing.m),
            ) {
                Image(
                    modifier = Modifier.width(width = 86.dp).height(height = 30.dp),
                    painter = painterResource(resource = Resources.Image.img_type_logo),
                    contentDescription = null,
                )

                Box(
                    modifier =
                        Modifier
                            .size(size = 38.dp)
                            .background(color = QUESTION_BADGE_COLOR, shape = CircleShape),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Q",
                        style = CaramelTheme.typography.heading1,
                        color = CaramelTheme.color.text.inverse,
                    )
                }

                Text(
                    modifier = Modifier.width(width = 261.dp),
                    text = question,
                    style = CaramelTheme.typography.heading2,
                    color = CaramelTheme.color.text.primary,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Box(
                    modifier =
                        Modifier
                            .width(width = 94.dp)
                            .height(height = 4.dp)
                            .clip(shape = CaramelTheme.shape.xxs)
                            .background(color = QUESTION_UNDERLINE_COLOR),
                )
            }

            ChoicesRow(
                myChoice = myChoice,
                partnerChoice = partnerChoice,
                myGender = myGender,
                partnerGender = partnerGender,
            )
        }

        MessageEmphasisBox(
            modifier =
                Modifier
                    .align(alignment = Alignment.TopCenter)
                    .graphicsLayer { translationY = 560.dp.toPx() },
            isSameChoice = isSameChoice,
        )
    }
}

@Composable
private fun ChoicesRow(
    myChoice: String,
    partnerChoice: String,
    myGender: Gender,
    partnerGender: Gender,
) {
    Box(contentAlignment = Alignment.TopCenter) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = CaramelTheme.spacing.l),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ChoiceColumn(
                label = "나는",
                labelColor = CaramelTheme.color.text.labelAccent1,
                gender = myGender,
                choice = myChoice,
                choiceTextColor = CaramelTheme.color.text.labelAccent1,
                choiceBackgroundColor = CHOICE_ME_BACKGROUND_COLOR,
                choiceBorderColor = CHOICE_ME_BORDER_COLOR,
            )
            ChoiceColumn(
                label = "상대방은",
                labelColor = CaramelTheme.color.text.labelAccent2,
                gender = partnerGender,
                choice = partnerChoice,
                choiceTextColor = CaramelTheme.color.text.labelAccent2,
                choiceBackgroundColor = CHOICE_PARTNER_BACKGROUND_COLOR,
                choiceBorderColor = CHOICE_PARTNER_BORDER_COLOR,
            )
        }

        Image(
            modifier =
                Modifier
                    .size(size = 48.dp)
                    .graphicsLayer { translationY = 42.5.dp.toPx() },
            painter = painterResource(resource = Res.drawable.img_balance_share_vs),
            contentDescription = null,
        )
    }
}

@Composable
private fun ChoiceColumn(
    label: String,
    labelColor: Color,
    gender: Gender,
    choice: String,
    choiceTextColor: Color,
    choiceBackgroundColor: Color,
    choiceBorderColor: Color,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = CaramelTheme.spacing.xs),
    ) {
        Text(
            text = label,
            style = CaramelTheme.typography.body3.bold,
            color = labelColor,
            textAlign = TextAlign.Center,
        )

        Image(
            modifier = Modifier.width(width = 105.dp).height(height = 136.dp),
            painter = painterResource(resource = characterResource(gender = gender)),
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )

        Box(
            modifier =
                Modifier
                    .width(width = 120.dp)
                    .clip(shape = CaramelTheme.shape.l)
                    .background(color = choiceBackgroundColor)
                    .border(
                        width = 1.dp,
                        color = choiceBorderColor,
                        shape = CaramelTheme.shape.l,
                    ).padding(
                        horizontal = CaramelTheme.spacing.m,
                        vertical = CaramelTheme.spacing.xs,
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = choice,
                style = CaramelTheme.typography.body1.bold,
                color = choiceTextColor,
                textAlign = TextAlign.Center,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun MessageEmphasisBox(
    isSameChoice: Boolean,
    modifier: Modifier = Modifier,
) {
    val message =
        buildAnnotatedString {
            if (isSameChoice) {
                append("오늘도 우리 ")
                withStyle(style = SpanStyle(color = CaramelTheme.color.text.brand)) {
                    append("취향 통했다")
                }
                append(" 💕")
            } else {
                append("달라도 ")
                withStyle(style = SpanStyle(color = CaramelTheme.color.text.brand)) {
                    append("귀여운 우리")
                }
                append(" 💕")
            }
        }

    Box(
        modifier =
            modifier
                .width(width = 261.dp)
                .background(color = CaramelTheme.color.background.tertiary, shape = CaramelTheme.shape.l)
                .border(
                    width = 1.dp,
                    color = MESSAGE_BOX_BORDER_COLOR,
                    shape = CaramelTheme.shape.l,
                ).padding(
                    horizontal = CaramelTheme.spacing.m,
                    vertical = CaramelTheme.spacing.s,
                ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = CaramelTheme.spacing.m),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(size = 20.dp),
                painter = painterResource(resource = Res.drawable.img_balance_share_message_heart),
                contentDescription = null,
                contentScale = ContentScale.Fit,
            )

            Text(
                text = message,
                style = CaramelTheme.typography.body3.bold,
                color = CaramelTheme.color.text.primary,
                textAlign = TextAlign.Center,
            )
        }
    }
}

private fun characterResource(gender: Gender): DrawableResource =
    when (gender) {
        Gender.FEMALE -> Res.drawable.img_balance_share_female
        else -> Res.drawable.img_balance_share_male
    }
