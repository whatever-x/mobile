package com.whatever.caramel.feature.profile.create.components.step

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.user.Gender
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun GenderStep(
    modifier: Modifier = Modifier,
    selectedGender: Gender,
    onClickGenderButton: (Gender) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "성별을\n알려주세요",
            style = CaramelTheme.typography.heading1,
            color = CaramelTheme.color.text.primary,
            textAlign = TextAlign.Center
        )

        Row (
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(
                space = CaramelTheme.spacing.m,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GenderButton(
                modifier = Modifier
                    .then(
                        if (selectedGender == Gender.MALE) {
                            Modifier.border(
                                width = 2.dp,
                                color = CaramelTheme.color.fill.brand,
                                shape = CaramelTheme.shape.xl
                            )
                        } else {
                            Modifier
                        }
                    ),
                text = "남자에요",
                icon = Resources.Icon.ic_male_90,
                onClick = { onClickGenderButton(Gender.MALE) },
            )

            GenderButton(
                modifier = Modifier
                    .then(
                        if (selectedGender == Gender.FEMALE) {
                            Modifier.border(
                                width = 2.dp,
                                color = CaramelTheme.color.fill.brand,
                                shape = CaramelTheme.shape.xl
                            )
                        } else {
                            Modifier
                        }
                    ),
                text = "여자에요",
                icon = Resources.Icon.ic_female_90,
                onClick = { onClickGenderButton(Gender.FEMALE) },
            )
        }
    }
}

@Composable
internal fun GenderButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: DrawableResource,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .width(width = 160.dp)
            .background(
                color = CaramelTheme.color.text.inverse, // TODO : 디자인 컬러 변경 확인
                shape = CaramelTheme.shape.xl
            )
            .clip(shape = CaramelTheme.shape.xl)
            .clickable(
                onClick = onClick,
                interactionSource = MutableInteractionSource(),
                indication = null
            )
            .padding(vertical = CaramelTheme.spacing.xl),
        verticalArrangement = Arrangement.spacedBy(
            space = CaramelTheme.spacing.l
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(size = 90.dp),
            painter = painterResource(resource = icon),
            tint = Color.Unspecified,
            contentDescription = null
        )

        Text(
            text = text,
            style = CaramelTheme.typography.body2.bold,
            color = CaramelTheme.color.text.primary
        )
    }
}