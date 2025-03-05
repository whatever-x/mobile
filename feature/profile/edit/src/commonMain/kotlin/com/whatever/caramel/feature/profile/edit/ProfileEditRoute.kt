package com.whatever.caramel.feature.profile.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feature.profile.edit.mvi.ProfileEditSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ProfileEditRoute(
    viewModel: ProfileEditViewModel = koinViewModel(),
    popBackStack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ProfileEditSideEffect.PopBackStack -> popBackStack()
            }
        }
    }

    ProfileEditScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}