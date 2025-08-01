package com.whatever.caramel.feature.setting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.components.shimmer
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.user.Gender
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun SettingUserProfile(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    gender: Gender,
    nickname: String,
    birthDay: String,
    isEditable: Boolean,
    onClickEditProfile: (() -> Unit)? = null,
) {
    if (isLoading) {
        SettingUserProfileSkeleton()
    } else {
        SettingUserProfileContent(
            modifier = modifier,
            gender = gender,
            nickname = nickname,
            birthDay = birthDay,
            isEditable = isEditable,
            onClickEditProfile = onClickEditProfile,
        )
    }
}

@Composable
internal fun SettingUserProfileContent(
    modifier: Modifier = Modifier,
    gender: Gender,
    nickname: String,
    birthDay: String,
    isEditable: Boolean,
    onClickEditProfile: (() -> Unit)? = null,
) {
    val genderImageResource =
        when (gender) {
            Gender.IDLE, Gender.MALE -> Resources.Image.img_gender_man
            Gender.FEMALE -> Resources.Image.img_gender_woman
        }
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    color = CaramelTheme.color.fill.inverse,
                    shape = CaramelTheme.shape.xl,
                ).padding(all = CaramelTheme.spacing.m),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(50.dp),
            contentDescription = null,
            painter = painterResource(genderImageResource),
            tint = Color.Unspecified,
        )
        Spacer(modifier = Modifier.size(size = CaramelTheme.spacing.m))
        Column(
            modifier =
                Modifier
                    .weight(1f),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = nickname,
                style = CaramelTheme.typography.body2.bold,
                color = CaramelTheme.color.text.primary,
            )
            Text(
                text = birthDay,
                style = CaramelTheme.typography.body3.regular,
                color = CaramelTheme.color.text.secondary,
            )
        }
        if (isEditable) {
            Box(
                modifier =
                    Modifier
                        .background(
                            shape = CaramelTheme.shape.s,
                            color = CaramelTheme.color.fill.quinary,
                        ).padding(
                            vertical = CaramelTheme.spacing.s,
                            horizontal = CaramelTheme.spacing.m,
                        ).clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = { onClickEditProfile?.invoke() },
                        ),
            ) {
                Text(
                    modifier =
                        Modifier
                            .align(Alignment.Center),
                    text = "편집",
                    style = CaramelTheme.typography.label1.bold,
                    color = CaramelTheme.color.text.brand,
                )
            }
        }
    }
}

@Composable
internal fun SettingUserProfileSkeleton(modifier: Modifier = Modifier) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    color = CaramelTheme.color.fill.inverse,
                    shape = CaramelTheme.shape.xl,
                ).padding(all = CaramelTheme.spacing.m),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(50.dp)
                    .shimmer(shape = CaramelTheme.shape.l),
        )
        Spacer(modifier = Modifier.size(size = CaramelTheme.spacing.m))
        Column(
            modifier =
                Modifier
                    .weight(1f),
            horizontalAlignment = Alignment.Start,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(width = 100.dp, height = 22.dp)
                        .shimmer(shape = CaramelTheme.shape.xs),
            )
            Spacer(modifier = Modifier.padding(bottom = CaramelTheme.spacing.xs))
            Box(
                modifier =
                    Modifier
                        .size(width = 70.dp, height = 16.dp)
                        .shimmer(shape = CaramelTheme.shape.xs),
            )
        }
    }
}
