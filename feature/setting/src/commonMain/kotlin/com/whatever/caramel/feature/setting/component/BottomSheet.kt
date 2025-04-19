package com.whatever.caramel.feature.setting.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun SettingEditProfileBottomSheet(
    navigateToProfileEditNickName : () -> Unit,
    navigateToProfileEditBrithDay : () -> Unit,
) {
    Column (
        modifier = Modifier
            .padding(horizontal = CaramelTheme.spacing.xl)
    ){
        Text(
            text = "프로필 편집",
            modifier = Modifier.padding(top = CaramelTheme.spacing.xxl),
            style = CaramelTheme.typography.heading3,
            color = CaramelTheme.color.text.primary
        )
        Spacer(modifier = Modifier.padding(top = CaramelTheme.spacing.s))
        SettingProfileChangeMenuItem(
            modifier = Modifier
                .padding(vertical = 14.dp),
            iconRes = Resources.Icon.ic_nickname_20,
            menu = "닉네임",
            onClick = navigateToProfileEditNickName
        )
        SettingProfileChangeMenuItem(
            modifier = Modifier
                .padding(vertical = 14.dp),
            iconRes = Resources.Icon.ic_gift_20,
            menu = "생일",
            onClick = navigateToProfileEditBrithDay
        )
        Spacer(modifier = Modifier.padding(top = 14.dp))
    }
}

@Composable
internal fun SettingProfileChangeMenuItem(
    modifier: Modifier = Modifier,
    iconRes: DrawableResource,
    menu: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = null
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier,
            painter = painterResource(iconRes),
            tint = CaramelTheme.color.icon.primary,
            contentDescription = null
        )
        Spacer(modifier = Modifier.padding(end = CaramelTheme.spacing.s))
        Text(
            text = menu,
            style = CaramelTheme.typography.body2.regular,
            color = CaramelTheme.color.text.primary
        )
    }
}