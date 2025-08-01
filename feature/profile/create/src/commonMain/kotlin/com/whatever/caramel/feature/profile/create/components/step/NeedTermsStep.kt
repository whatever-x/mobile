package com.whatever.caramel.feature.profile.create.components.step

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.CaramelBalloonPopupWithImage
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun NeedTermsStep(
    modifier: Modifier = Modifier,
    isServiceTermChecked: Boolean,
    isPersonalInfoTermChecked: Boolean,
    onClickServiceTerm: () -> Unit,
    onClickPersonalInfoTerm: () -> Unit,
    onClickServiceTermLabel: () -> Unit,
    onClickPersonalInfoLabel: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "카라멜을 사용하려면\n아래 동의가 필요해요.",
            style = CaramelTheme.typography.heading1,
            color = CaramelTheme.color.text.primary,
            textAlign = TextAlign.Center,
        )

        CaramelBalloonPopupWithImage(
            modifier = Modifier.weight(weight = 1f),
            text = "동의만 하면 가입 끝!\n연인과 함께 똑똑하고 달달한 연애해봐요!",
            image = {
                Box {
                    Box(
                        modifier =
                            Modifier
                                .offset(y = 55.dp)
                                .fillMaxWidth()
                                .height(height = 125.dp)
                                .background(
                                    brush =
                                        Brush.verticalGradient(
                                            colors =
                                                listOf(
                                                    Color(0xFFFFE6C3),
                                                    Color(0xFFFFE6C3).copy(alpha = 0f),
                                                ),
                                        ),
                                ),
                    )

                    Image(
                        modifier =
                            Modifier
                                .align(alignment = Alignment.TopCenter)
                                .size(
                                    width = 170.dp,
                                    height = 80.dp,
                                ),
                        painter = painterResource(resource = Resources.Image.img_couple_on_ground),
                        contentDescription = null,
                    )
                }
            },
        )

        Column(
            modifier =
                Modifier
                    .padding(bottom = CaramelTheme.spacing.xxl),
        ) {
            CheckTerm(
                text = "(필수) 서비스 동의약관",
                isChecked = isServiceTermChecked,
                onClickCheckBox = onClickServiceTerm,
                onClickLabel = onClickServiceTermLabel,
            )

            CheckTerm(
                text = "(필수) 개인정보 수집/이용 동의약관",
                isChecked = isPersonalInfoTermChecked,
                onClickCheckBox = onClickPersonalInfoTerm,
                onClickLabel = onClickPersonalInfoLabel,
            )
        }
    }
}

@Composable
private fun CheckTerm(
    modifier: Modifier = Modifier,
    text: String,
    isChecked: Boolean,
    onClickCheckBox: () -> Unit,
    onClickLabel: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(
                    vertical = CaramelTheme.spacing.s,
                    horizontal = CaramelTheme.spacing.xl,
                ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier =
                Modifier
                    .clickable(
                        onClick = onClickCheckBox,
                        interactionSource = null,
                        indication = null,
                    ),
            horizontalArrangement =
                Arrangement.spacedBy(
                    space = CaramelTheme.spacing.s,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter =
                    painterResource(
                        resource =
                            if (isChecked) {
                                Resources.Icon.ic_check_box_20
                            } else {
                                Resources.Icon.ic_uncheck_box_20
                            },
                    ),
                tint = Color.Unspecified,
                contentDescription = null,
            )

            Text(
                text = text,
                style = CaramelTheme.typography.body2.regular,
                color = CaramelTheme.color.text.primary,
            )
        }

        Text(
            modifier =
                Modifier
                    .clickable(
                        onClick = onClickLabel,
                        indication = null,
                        interactionSource = null,
                    ),
            text = "자세히보기",
            style = CaramelTheme.typography.label1.regular,
            color = CaramelTheme.color.text.secondary,
        )
    }
}
