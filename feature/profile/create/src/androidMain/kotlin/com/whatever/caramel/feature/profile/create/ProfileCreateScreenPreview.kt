package com.whatever.caramel.feature.profile.create

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Preview
@Composable
private fun ProfileCreateScreenPreview(
    @PreviewParameter(ProfileCreateScreenPreviewProvider::class) data: ProfileCreateScreenPreviewData
) {
    CaramelTheme {
        ProfileCreateScreen(
            state = data.state,
            onIntent = { }
        )
    }
}