package com.whatever.caramel.feature.setting.component.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.domain.vo.user.Gender
import com.whatever.caramel.feature.setting.component.SettingUserProfile

@Preview(showBackground = true)
@Composable
fun UserProfilePreview() {
    CaramelTheme {
        SettingUserProfile(
            gender = Gender.FEMALE,
            nickname = "유승우",
            birthDay = "1997.11.18",
            isEditable = false,
        )
    }
}
