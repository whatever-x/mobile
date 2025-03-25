package com.whatever.caramel.feature.copule.invite

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.feature.copule.invite.mvi.CoupleInviteState

@Preview
@Composable
private fun CoupleInviteScreenPreview() {
    CaramelTheme {
        CoupleInviteScreen(
            state = CoupleInviteState(),
            onIntent = {}
        )
    }
}