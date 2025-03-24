package com.whatever.caramel.feature.profile.create

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.profile.create.mvi.Gender
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateState
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateStep

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