package com.whatever.caramel.feature.profile.edit.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.profile.edit.ProfileEditScreen

@Preview
@Composable
private fun ProfileEditPreview(
    @PreviewParameter(ProfileEditPreviewDataProvider::class) data: ProfileEditPreviewData,
) {
    CaramelTheme {
        ProfileEditScreen(
            state = data.state,
            onIntent = { },
        )
    }
}
