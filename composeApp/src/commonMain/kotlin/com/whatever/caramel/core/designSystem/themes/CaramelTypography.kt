package com.whatever.caramel.core.designSystem.themes

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// @ham2174 TODO : font, typo token 들 확정 되면 추가 하기
@Immutable
data class CaramelTypography(
    val title1: TextStyle
) {
    companion object {
        fun defaultTypography(fontFamily: FontFamily): CaramelTypography = CaramelTypography(
            title1 = TextStyle(
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                letterSpacing = 0.sp,
                lineHeight = 41.6f.sp,
            ),
        )
    }
}