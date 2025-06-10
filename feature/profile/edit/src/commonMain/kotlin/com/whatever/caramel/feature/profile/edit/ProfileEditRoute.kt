package com.whatever.caramel.feature.profile.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.core.designsystem.util.HapticController
import com.whatever.caramel.core.designsystem.util.HapticStyle
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditSideEffect
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ProfileEditRoute(
    viewModel: ProfileEditViewModel = koinViewModel(),
    popBackStack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val hapticController: HapticController = getKoin().get()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ProfileEditSideEffect.PopBackStack -> popBackStack()
                ProfileEditSideEffect.PerformHapticFeedback -> hapticController.performImpact(
                    HapticStyle.GestureThresholdActivate)
            }
        }
    }

    ProfileEditScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}