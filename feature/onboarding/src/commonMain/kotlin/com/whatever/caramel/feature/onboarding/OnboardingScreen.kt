package com.whatever.caramel.feature.onboarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.whatever.caramel.feature.onboarding.components.OnBoardingBox
import com.whatever.caramel.feature.onboarding.mvi.OnboardingIntent
import com.whatever.caramel.feature.onboarding.mvi.OnboardingState

@Composable
internal fun OnboardingScreen(
    state: OnboardingState,
    onIntent: (OnboardingIntent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onIntent(OnboardingIntent.ClickNextButton)}
            ) {
                Text(
                    text = "다음",
                    fontSize = 12.sp
                )
            }
        }
    ) {
        OnBoardingBox()
    }
}