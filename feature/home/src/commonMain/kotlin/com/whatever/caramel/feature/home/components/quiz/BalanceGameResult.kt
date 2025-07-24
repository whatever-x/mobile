package com.whatever.caramel.feature.home.components.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.feature.home.mvi.BalanceGameOptionState
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun BalanceGameResult(
    modifier: Modifier = Modifier,
    myNickname: String,
    myGender: Gender,
    partnerNickname: String,
    partnerGender: Gender,
    myChoiceOption: BalanceGameOptionState,
    partnerChoiceOption: BalanceGameOptionState,
) {
    val myGenderImage =
        when (myGender) {
            Gender.MALE -> Resources.Image.img_quiz_man
            Gender.FEMALE -> Resources.Image.img_quiz_woman
            else -> Resources.Image.img_quiz_man
        }

    val partnerGenderImage =
        when (partnerGender) {
            Gender.MALE -> Resources.Image.img_quiz_man
            Gender.FEMALE -> Resources.Image.img_quiz_woman
            else -> Resources.Image.img_quiz_man
        }

    Column(
        modifier = modifier,
        verticalArrangement =
            Arrangement.spacedBy(
                space = CaramelTheme.spacing.l,
                alignment = Alignment.CenterVertically,
            ),
    ) {
        repeat(2) { index ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(
                        space = CaramelTheme.spacing.m,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier.size(size = 50.dp),
                    painter =
                        painterResource(
                            resource =
                                if (index == 0) {
                                    myGenderImage
                                } else {
                                    partnerGenderImage
                                },
                        ),
                    contentDescription = null,
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement =
                        Arrangement.spacedBy(
                            space = CaramelTheme.spacing.xxs,
                        ),
                ) {
                    Text(
                        text = if (index == 0) myNickname else partnerNickname,
                        style = CaramelTheme.typography.body4.regular,
                        color = CaramelTheme.color.text.secondary,
                    )

                    Text(
                        text = if (index == 0) myChoiceOption.name else partnerChoiceOption.name,
                        style = CaramelTheme.typography.body1.bold,
                        color = CaramelTheme.color.text.primary,
                    )
                }
            }
        }
    }
}
