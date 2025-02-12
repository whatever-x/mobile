package com.whatever.caramel.feature.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

class OnboardingScreenRoot : Screen {
    @Composable
    override fun Content() {
        OnboardingScreen()
    }
}

@Composable
fun OnboardingScreen() {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Onboarding",
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
        )
    }
}

