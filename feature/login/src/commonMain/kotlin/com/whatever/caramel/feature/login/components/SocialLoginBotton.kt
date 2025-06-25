package com.whatever.caramel.feature.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import caramel.feature.login.generated.resources.Res
import caramel.feature.login.generated.resources.apple
import caramel.feature.login.generated.resources.kakao
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.auth.SocialLoginType
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SocialLoginButton(
    socialLoginType: SocialLoginType,
    onLaunch: () -> Unit
) {
    val backgroundColor = when (socialLoginType) {
        SocialLoginType.KAKAO -> Color(0xFFFFE812)
        SocialLoginType.APPLE -> Color.Black
    }
    val drawableResource = when (socialLoginType) {
        SocialLoginType.KAKAO -> Resources.Icon.ic_kakao_logo_24
        SocialLoginType.APPLE -> Resources.Icon.ic_apple_logo_17_20
    }
    val stringResource = when (socialLoginType) {
        SocialLoginType.KAKAO -> Res.string.kakao
        SocialLoginType.APPLE -> Res.string.apple
    }
    val textColor = when (socialLoginType) {
        SocialLoginType.KAKAO -> CaramelTheme.color.text.primary
        SocialLoginType.APPLE -> CaramelTheme.color.text.inverse
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 50.dp)
            .background(
                color = backgroundColor,
                shape = CaramelTheme.shape.xl
            )
            .clip(shape = CaramelTheme.shape.xl)
            .clickable(onClick = onLaunch)
    ) {
        Icon(
            modifier = Modifier
                .offset(x = 12.dp)
                .align(alignment = Alignment.CenterStart),
            painter = painterResource(resource = drawableResource),
            tint = Color.Unspecified,
            contentDescription = null
        )

        Text(
            modifier = Modifier.align(alignment = Alignment.Center),
            text = stringResource(resource = stringResource),
            style = CaramelTheme.typography.body1.bold,
            color = textColor
        )
    }
}