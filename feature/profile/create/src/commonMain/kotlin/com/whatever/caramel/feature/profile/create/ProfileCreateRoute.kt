package com.whatever.caramel.feature.profile.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ProfileCreateRoute(
    viewModel: ProfileCreateViewModel = koinViewModel(),
    navigateToLogin: () -> Unit,
    navigateToConnectCouple: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ProfileCreateSideEffect.NavigateToLogin -> navigateToLogin()
                is ProfileCreateSideEffect.NavigateToConnectCouple -> navigateToConnectCouple()
                is ProfileCreateSideEffect.NavigateToPersonalInfoTermNotion -> {} // @ham2174 TODO : 노션 링크 이동
                is ProfileCreateSideEffect.NavigateToServiceTermNotion -> {} // @ham2174 TODO : 노션 링크 이동
            }
        }
    }

    ProfileCreateScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}